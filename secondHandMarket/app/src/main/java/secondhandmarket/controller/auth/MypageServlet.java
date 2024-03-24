package secondhandmarket.controller.auth;

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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                req.setAttribute("viewUrl", "/app/home");
                return;
            }
            Photo userPhoto = userPhotoDao.findBy(loginUser.getNo());
            if (userPhoto == null) {
                Photo photo = new Photo();
                photo.setPath("defaultUser.png");
                loginUser.setPhoto(photo);
            } else {
                loginUser.setPhoto(userPhoto);
            }
            req.setAttribute("userPhoto", userPhoto);
            List<Goods> goodsOfUser = goodsDao.findAll(loginUser.getNo());
            for (Goods goods : goodsOfUser) {
                List<Photo> photoList = goodsPhotoDao.findBy(goods.getNo());
                goods.setPhotoList(photoList);
            }
            req.setAttribute("goodsOfUser", goodsOfUser);
            req.setAttribute("viewUrl", "/auth/mypage.jsp");

        } catch (Exception e) {
            req.setAttribute("message", "마이페이지 불러오기 오류");
            req.setAttribute("exception", e);
            req.setAttribute("viewUrl", "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String nickname = req.getParameter("nickname");
            String phoneNo = req.getParameter("phoneNo");
            User loginUser = (User) req.getSession().getAttribute("loginUser");

            Photo profilePhoto = userPhotoDao.findBy(loginUser.getNo());
            boolean nullFlag = false;

            if (profilePhoto == null) {
                nullFlag = true;
                profilePhoto = new Photo();
                profilePhoto.setRefNo(loginUser.getNo());
            }

            Collection<Part> parts = req.getParts();
            for (Part part : parts) {
                if (!part.getName().equals("photo") || part.getSize() == 0) {
                    continue;
                } else {
                    String filename = UUID.randomUUID().toString();
                    part.write(this.userPhotoDir + "/" + filename);
                    profilePhoto.setPath(filename);
                }
            }
            loginUser.setNickname(nickname);
            loginUser.setPhoneNo(phoneNo);
            loginUser.setPhoto(profilePhoto);
            userDao.updateInfo(loginUser);
            if (nullFlag) {
                userPhotoDao.add(profilePhoto);
            } else {
                userPhotoDao.update(profilePhoto);
            }
            userPhotoDao.update(profilePhoto);
            req.setAttribute("viewUrl", "redirect:/auth/mypage");
        } catch (Exception e) {
            req.setAttribute("message", "마이페이지 회원정보 변경 오류");
            req.setAttribute("exception", e);
            req.setAttribute("viewUrl", "/error");
        }
    }
}
