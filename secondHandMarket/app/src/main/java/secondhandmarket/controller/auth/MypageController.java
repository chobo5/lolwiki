package secondhandmarket.controller.auth;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MypageController implements PageController {

    private UserDaoImpl userDao;
    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private UserPhotoDaoImpl userPhotoDao;
    private String goodsDir;
    private String userPhotoDir;

    public MypageController(UserDaoImpl userDao, GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao,
                            UserPhotoDaoImpl userPhotoDao, String goodsDir, String userPhotoDir) {
        this.userDao = userDao;
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.userPhotoDao = userPhotoDao;
        this.goodsDir = goodsDir;
        this.userPhotoDir = userPhotoDir;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (req.getMethod().equals("get")) {
            try {
                if (loginUser == null) {
                    return"/app/home";
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
                return "/auth/mypage.jsp";

            } catch (Exception e) {
                req.setAttribute("message", "마이페이지 불러오기 오류");
                req.setAttribute("exception", e);
                return "/error.jsp";
            }
        }

        try {
            String nickname = req.getParameter("nickname");
            String phoneNo = req.getParameter("phoneNo");

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
            return "redirect:/auth/mypage";
        } catch (Exception e) {
            req.setAttribute("message", "마이페이지 회원정보 변경 오류");
            req.setAttribute("exception", e);
             return "/error";
        }
    }
}
