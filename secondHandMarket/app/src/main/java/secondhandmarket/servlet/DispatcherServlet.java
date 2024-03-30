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
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                                                    Map<String, Object> map) {
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
                }
            }




        }

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
