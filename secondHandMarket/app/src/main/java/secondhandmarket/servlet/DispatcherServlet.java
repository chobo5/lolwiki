package secondhandmarket.servlet;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import secondhandmarket.controller.*;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.*;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
    private static final Log log = LogFactory.getLog(DispatcherServlet.class);
    private List<Object> controllers = new ArrayList<>();
    private Map<String, RequestHandler> requestHandlerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        log.debug("생성됨");
        ServletContext context = this.getServletContext();
        TransactionManager txManager = (TransactionManager) context.getAttribute("txManager");
        GoodsDaoImpl goodsDao = (GoodsDaoImpl) context.getAttribute("goodsDao");
        GoodsPhotoDaoImpl goodsPhotoDao = (GoodsPhotoDaoImpl) context.getAttribute("goodsPhotoDao");
        UserDaoImpl userDao = (UserDaoImpl) context.getAttribute("userDao");
        UserPhotoDaoImpl userPhotoDao = (UserPhotoDaoImpl) context.getAttribute("userPhotoDao");
        String goodsUploadDir = context.getRealPath("/upload/goods");
        String userUploadDir = context.getRealPath("/upload/user");

        controllers.add(new HomeController());
        controllers.add(new AuthController(txManager, userDao, goodsDao, goodsPhotoDao, userPhotoDao, goodsUploadDir, userUploadDir));
        controllers.add(new GoodsController(goodsDao, goodsPhotoDao, goodsUploadDir, txManager));
        prepareRequestHandler(controllers);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //미리 준비한 requestHandlerMao에서 적절한 requestHandler를 찾는다.
            RequestHandler requestHandler = requestHandlerMap.get(req.getPathInfo());
            if (requestHandler == null) {
                throw new Exception(req.getPathInfo() + "요청 페이지를 찾을 수 없습니다.");
            }

            //pageController가 작업한 결과를 담을 map을 준비한다.
            Map<String, Object> map = new HashMap<>();
            //pageController의 requestHandler의 파라미터들을 알아낸다.
            Object[] args = prepareRequestHandlerArguments(requestHandler.handler, req, resp, map);

            String viewUrl = (String) requestHandler.handler.invoke(requestHandler.controller, req, resp);

            if (viewUrl.startsWith("redirect:")) {
                resp.sendRedirect(viewUrl.substring(9));
            } else {
                req.getRequestDispatcher(viewUrl).forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("message", req.getPathInfo() + " 실행 오류");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            req.setAttribute("detail", printWriter.toString());

            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    private void prepareRequestHandler(List<Object> controllers) {
        for (Object controller : controllers) {
            Method[] methods = controller.getClass().getMethods();
            for (Method m : methods) {
                RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
                if (requestMapping != null) {
                    requestHandlerMap.put(requestMapping.value(), new RequestHandler(controller, m));
                }
            }
        }
    }

    private Object[] prepareRequestHandlerArguments(Method handler,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response,
                                                    Map<String, Object> map) throws Exception {
        Parameter[] params = handler.getParameters();
        Object[] args = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].getType() == ServletRequest.class || params[i].getType() == HttpServletRequest.class) {
                args[i] = request;
            } else if ((params[i].getType() == ServletResponse.class || params[i].getType() == HttpServletResponse.class)) {
                args[i] = response;
            } else if (params[i].getType() == Map.class) {
                args[i] = map;
            } else if (params[i].getType() == HttpSession.class) {
                args[i] = request.getSession();
            } else {
                CookieValue cookieValueAnno = params[i].getAnnotation(CookieValue.class);
                if (cookieValueAnno != null) {
                    String cookieValue = getCookieValue(cookieValueAnno.value(), request);
                    if (cookieValue != null) {
                        args[i] = valueOf(cookieValue, params[i].getType());
                    }
                    continue;
                }
            }


            RequestParam requestParam = params[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                String requestParamName= requestParam.value();
                if (params[i].getType() == Part[].class) {
                    Collection<Part> parts = request.getParts();
                    List<Part> fileParts = new ArrayList<>();
                    for (Part part : parts) {
                        if (part.getName().equals(requestParamName)) {
                            fileParts.add(part);
                        }
                    }
                    args[i] = fileParts.toArray(new Part[0]);
                } else if (params[i].getType() == Part.class) {
                    Collection<Part> parts = request.getParts();
                    for (Part part : parts) {
                        if (part.getName().equals(requestParamName)) {
                            args[i] = part;
                            break;
                        }
                    }
                } else {
                    args[i] = request.getParameter(requestParamName);
                }
                continue;
            }

            args[i] = createValueObject(params[i].getType(), request);

        }
        return args;
    }

    private Object createValueObject(Class<?> type, HttpServletRequest request) throws Exception {
        Constructor constructor = type.getConstructor();
        Object obj = constructor.newInstance();
        Method[] methods = type.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("set")) {
                String propName = Character.toLowerCase(m.getName().charAt(3)) + m.getName().substring(4);
                if (request.getParameter(propName) != null) {
                    Class<?> setterParamType = m.getParameters()[0].getType();
                    m.invoke(obj, valueOf(propName, setterParamType));
                }
            }
        }
        return obj;
    }

    private Object valueOf(String strValue, Class<?> type) {
        if (type == byte.class) {
            return Byte.parseByte(strValue);
        } else if (type == short.class) {
            return Short.valueOf(strValue);
        } else if (type == int.class) {
            return Integer.parseInt(strValue);
        } else if (type == long.class) {
            return Long.parseLong(strValue);
        } else if (type == float.class) {
            return Float.parseFloat(strValue);
        } else if (type == double.class) {
            return Double.parseDouble(strValue);
        } else if (type == boolean.class) {
            return Boolean.parseBoolean(strValue);
        } else if (type == char.class) {
            return strValue.charAt(0);
        } else if (type == Date.class) {
            return Date.valueOf(strValue);
        } else if (type == String.class) {
            return strValue;
        }
        return null;
    }

    private String getCookieValue(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie != null && cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
