package secondhandmarket.servlet;

import secondhandmarket.controller.HomeController;
import secondhandmarket.controller.PageController;
import secondhandmarket.controller.auth.*;
import secondhandmarket.controller.goods.*;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

    private Map<String, PageController> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        ServletContext context = this.getServletContext();
        TransactionManager txManager = (TransactionManager) context.getAttribute("txManager");
        GoodsDaoImpl goodsDao = (GoodsDaoImpl) context.getAttribute("goodsDao");
        GoodsPhotoDaoImpl goodsPhotoDao = (GoodsPhotoDaoImpl) context.getAttribute("goodsPhotoDao");
        UserDaoImpl userDao = (UserDaoImpl) context.getAttribute("userDao");
        UserPhotoDaoImpl userPhotoDao = (UserPhotoDaoImpl) context.getAttribute("userPhotoDao");

        controllerMap.put("/home", new HomeController());

        String goodsUploadDir = context.getRealPath("/upload/goods");
        String userUploadDir = context.getRealPath("/upload/user");
        controllerMap.put("/auth/changepw", new ChangePwController(userDao));
        controllerMap.put("/auth/join", new JoinController(userDao, userPhotoDao, userUploadDir, txManager));
        controllerMap.put("/auth/login", new LoginController(userDao));
        controllerMap.put("/auth/logout", new LogoutController());
        controllerMap.put("/auth/mypage", new MypageController(userDao, goodsDao, goodsPhotoDao, userPhotoDao, goodsUploadDir, userUploadDir));

        controllerMap.put("/goods/add", new GoodsAddController(goodsDao, goodsPhotoDao, goodsUploadDir, txManager));
        controllerMap.put("/goods/delete", new GoodsDeleteController(goodsDao, goodsPhotoDao, txManager));
        controllerMap.put("/goods/list", new GoodsListController(goodsDao, goodsPhotoDao, goodsUploadDir));
        controllerMap.put("/goods/modify", new GoodsModifyController(goodsDao, goodsPhotoDao, goodsUploadDir));
        controllerMap.put("goods/delete/photo", new GoodsPhotoDeleteController(goodsPhotoDao));
        controllerMap.put("/goods/view", new GoodsViewController(goodsDao, goodsPhotoDao, goodsUploadDir));

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getPathInfo()).include(req, resp);

        PageController pageController = controllerMap.get(req.getPathInfo());
        if (pageController == null) {
            throw new ServletException(req.getPathInfo() + "요청 페이지르 찾을 수 없습니다.");
        }

        try {
            String viewUrl = (String) req.getAttribute("viewUrl");
            if (viewUrl.startsWith("redirect:")) {
                resp.sendRedirect(viewUrl.substring(9));
            } else {
                req.getRequestDispatcher(viewUrl).forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("message", req.getPathInfo() + "실행 오류");
            StringWriter stringWriter =  new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            e.printStackTrace(printWriter);
            req.setAttribute("detail", printWriter.toString());

            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
