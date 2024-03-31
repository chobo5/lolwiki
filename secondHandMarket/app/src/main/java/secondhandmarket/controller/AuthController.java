package secondhandmarket.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import secondhandmarket.controller.RequestMapping;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.http.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AuthController {
    private static final Log log = LogFactory.getLog(AuthController.class);
    private TransactionManager txManager;
    private UserDaoImpl userDao;
    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private UserPhotoDaoImpl userPhotoDao;
    private String goodsPhotoDir;
    private String userPhotoDir;

    public AuthController(TransactionManager txManager, UserDaoImpl userDao, GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao,
                          UserPhotoDaoImpl userPhotoDao, String goodsPhotoDir, String userPhotoDir) {
        this.txManager = txManager;
        this.userDao = userDao;
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.userPhotoDao = userPhotoDao;
        this.goodsPhotoDir = goodsPhotoDir;
        this.userPhotoDir = userPhotoDir;
    }

    @RequestMapping("/auth/join_form")
    public String joinForm() {
        return "/auth/join_form.jsp";
    }

    @RequestMapping("/auth/join")
    public String join(@RequestParam("nickname") String nickname,
                       @RequestParam("phoneNo") String phoneNo,
                       @RequestParam("password1") String password1,
                       @RequestParam("password2") String password2,
                       @RequestParam("file") Part file,
                       Map<String, Object> map) throws Exception {
        try {
            Photo photo = new Photo();
            if (file.getSize() != 0) {
                String filename = UUID.randomUUID().toString();
                file.write(this.userPhotoDir + "/" + filename);
                photo.setPath(filename);
            }


            if (!password1.equals(password2)) {
                throw new Exception("비밀번호가 일치하지 않습니다.");
            } else if (userDao.findBy(nickname) != null) {
                throw new Exception("이미 존재하는 닉네임 입니다.");
            } else {
                User user = new User();
                user.setNickname(nickname);
                user.setPhoneNo(phoneNo);
                user.setPassword(password1);
                user.setPhoto(photo);

                txManager.startTransaction();
                userDao.add(user);
                photo.setRefNo(user.getNo());
                userPhotoDao.add(photo);
                txManager.commit();
                return "/home.jsp";
            }

        } catch (Exception e) {
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            throw new Exception("회원가입 오류");
        }
    }

    @RequestMapping("/auth/login_form")
    public String loginForm(@CookieValue("savedNickname") String savedNickname,
                            Map<String, Object> map) {
        log.debug("/auth/login_form");
        map.put("savedNickname", savedNickname);
        return "/auth/login_form.jsp";
    }

    @RequestMapping("/auth/login")
    public String login(@RequestParam("nickname") String nickname,
                        @RequestParam("password") String password,
                        @RequestParam("saveNickname") String saveNickname,
                        HttpServletResponse resp,
                        HttpSession session) throws Exception {


        User loginUser = userDao.findByNicknameAndPassword(nickname, password);
        if (loginUser == null) {
            throw new Exception("닉네임 또는 비밀번호가 일치하지 않습니다.");
        } else {
            session.setAttribute("loginUser", loginUser);
            if (saveNickname != null) {
                Cookie cookie = new Cookie("savedNickname", nickname);
                cookie.setMaxAge(60 * 60 * 24 * 7);
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("savedNickname", "");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            return "redirect:/app/home";
        }
    }

    @RequestMapping("/auth/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/app/home";
    }

    @RequestMapping("/auth/mypage_form")
    public String mypageForm(HttpSession session, Map<String, Object> map) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        try {
            if (loginUser == null) {
                return "/auth/login_form.jsp";
            }
            Photo userPhoto = userPhotoDao.findBy(loginUser.getNo());
            if (userPhoto == null) {
                Photo photo = new Photo();
                photo.setPath("defaultUser.png");
                loginUser.setPhoto(photo);
            } else {
                loginUser.setPhoto(userPhoto);
            }
            map.put("userPhoto", userPhoto);
            List<Goods> goodsOfUser = goodsDao.findAll(loginUser.getNo());
            for (Goods goods : goodsOfUser) {
                List<Photo> photoList = goodsPhotoDao.findBy(goods.getNo());
                goods.setPhotoList(photoList);
            }
            map.put("goodsOfUser", goodsOfUser);
            return "/auth/mypage_form.jsp";

        } catch (Exception e) {
            throw new Exception("마이페이지 불러오기 오류");
        }
    }

    @RequestMapping("/auth/mypage")
    public String mypage(HttpSession session,
                         @RequestParam("nickname") String nickname,
                         @RequestParam("phoneNo") String phoneNo,
                         @RequestParam("file") Part file) throws Exception {

        try {
            User loginUser = (User) session.getAttribute("loginUser");
            Photo profilePhoto = userPhotoDao.findBy(loginUser.getNo());
            boolean nullFlag = false;

            if (profilePhoto == null) {
                nullFlag = true;
                profilePhoto = new Photo();
                profilePhoto.setRefNo(loginUser.getNo());
            }
            if (file != null) {
                String filename = UUID.randomUUID().toString();
                file.write(this.userPhotoDir + "/" + filename);
                profilePhoto.setPath(filename);
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
            return "redirect:/app/auth/mypage_form";
        } catch (Exception e) {
            throw new Exception("마이페이지 회원정보 변경 오류");
        }
    }

    @RequestMapping("/auth/changepw_form")
    public String changePwForm(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "/auth/login_form";
        }
        return "/auth/changepw_form.jsp";
    }


    @RequestMapping("/auth/changepw")
    public String changePw(HttpSession session,
                           @RequestParam("currentpw") String currentpw,
                           @RequestParam("newpw1") String newpw1,
                           @RequestParam("newpw2") String newpw2) throws Exception {
        try {
            User loginUser = (User) session.getAttribute("loginUser");
            User user = userDao.findByNicknameAndPassword(loginUser.getNickname(), currentpw);
            if (user == null || (loginUser.getNo() != user.getNo()) || !newpw1.equals(newpw2)) {
                throw new Exception("비밀번호가 일치하지 않습니다.");
            } else {
                loginUser.setPassword(newpw1);
                userDao.updatePassword(loginUser);
                return "redirect:/app/auth/mypage_form";
            }
        } catch (Exception e) {
            throw new Exception("비밀번호 변경 오류");
        }

    }


}
