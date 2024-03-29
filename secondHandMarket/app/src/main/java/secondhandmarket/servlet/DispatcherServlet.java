package secondhandmarket.servlet;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import secondhandmarket.controller.AuthController;
import secondhandmarket.controller.GoodsController;
import secondhandmarket.controller.HomeController;
import secondhandmarket.controller.RequestMapping;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
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
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        prepareRequestHandler(controllers);
        RequestHandler requestHandler = requestHandlerMap.get(req.getPathInfo());

        try {
            if (requestHandler == null) {
                throw new Exception(req.getPathInfo() + "요청 페이지를 찾을 수 없습니다.");
            }

            String viewUrl = (String) requestHandler.handler.invoke(requestHandler.controller, req, resp);

            if (viewUrl.startsWith("redirect:")) {
                resp.sendRedirect(viewUrl.substring(9));
            } else {
                req.getRequestDispatcher(viewUrl).forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("message", req.getPathInfo() + " 실행 오류");
            StringWriter stringWriter =  new StringWriter();
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
                    requestHandlerMap.put(requestMapping.value(), new RequestHandler(controller,m));
                }
            }
        }
    }

}
