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
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        req.getRequestDispatcher("/header").include(req, resp);
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                resp.sendRedirect("/home");
            }
            Photo userPhoto = userPhotoDao.findBy(loginUser.getNo());
            if (userPhoto == null) {
                Photo photo = new Photo();
                photo.setPath("defaultUser.png");
                loginUser.setPhoto(photo);
            } else {
                loginUser.setPhoto(userPhoto);
            }
            List<Goods> goodsOfUser = goodsDao.findAll(loginUser.getNo());
            for (Goods goods : goodsOfUser) {
                List<Photo> photoList = goodsPhotoDao.findBy(goods.getNo());
                goods.setPhotoList(photoList);
            }
            out.printf("<h3>%s</h3>\n", loginUser.getNickname());
            out.printf("<img src='%s' width=250 height=250'>\n",  "/upload/user/" + loginUser.getPhoto().getPath());
            out.printf("<h4>%s</h4>\n", loginUser.getPhoneNo());
            out.println("<br>");
            out.println("<br>");
            out.printf("<h3>%s</h3>\n", "판매 상품");
            for (Goods goods : goodsOfUser) {
                out.printf("<p><a href='>%s</a></p>\n", goods.getName());
                for (Photo photo : goods.getPhotoList()) {
                    out.printf("<img src='%s' width=250 height=250'>\n", "/upload/goods/" + photo.getPath());
                }
                out.printf("<p>%s</p>\n", goods.getPrice());
                out.printf("<p>%s</p>\n", goods.getSpec());
                out.printf("<p>%s</p>\n", goods.getRegDate());
                out.println("<p>--------------------------------------------------------</p>");
            }
            req.getRequestDispatcher("/footer").include(req, resp);

        } catch (Exception e) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


}
