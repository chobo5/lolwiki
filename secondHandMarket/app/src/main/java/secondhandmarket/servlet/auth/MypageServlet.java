package secondhandmarket.servlet.auth;

import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
@WebServlet("/auth/mypage")
public class MypageServlet extends HttpServlet {
    private UserDaoImpl userDao;
    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private UserPhotoDaoImpl userPhotoDao;
    private String goodsDir;
    private String userPhotoDir;
    @Override
    public void init() throws ServletException {
        userDao = (UserDaoImpl) this.getServletContext().getAttribute("userDao");
        goodsDao = (GoodsDaoImpl) this.getServletContext().getAttribute("goodsDao");
        goodsPhotoDao = (GoodsPhotoDaoImpl) this.getServletContext().getAttribute("goodsPhotoDao");
        userPhotoDao = (UserPhotoDaoImpl) this.getServletContext().getAttribute("userPhotoDao");
        goodsDir =  this.getServletContext().getRealPath("/upload/goods");
        userPhotoDir =  this.getServletContext().getRealPath("/upload/user");

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        resp.setContentType("text/html;charse=UTF-8");
        PrintWriter out = resp.getWriter();
        req.getRequestDispatcher("/header").include(req, resp);
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                resp.sendRedirect("/home");
            }
            Photo profilePhto = userPhotoDao.findBy(loginUser.getNo());
            List<Goods> goodsOfUser = goodsDao.findAll(loginUser.getNo());
            for (Goods goods : goodsOfUser) {
                List<Photo> photoList = goodsPhotoDao.findBy(goods.getNo());
                goods.setPhotoList(photoList);
            }

        } catch (Exception e) {
            
        }
        req.getRequestDispatcher("/footer").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
