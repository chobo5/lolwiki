package secondhandmarket.controller;

import secondhandmarket.controller.RequestMapping;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class AuthController {
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


    @RequestMapping("/auth/join")
    public String join(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getMethod().equals("GET")) {
            return "/auth/join.jsp";
        }

        String nickName = req.getParameter("nickname");
        String phoneNo = req.getParameter("phoneNo");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        try {
            Photo profilePhoto = new Photo();
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

            if (!password1.equals(password2)) {
                req.setAttribute("message", "비밀번호가 일치하지 않습니다.");
                return "/error.jsp";
            } else if (userDao.findBy(nickName) != null) {
                req.setAttribute("message", "이미 존재하는 닉네임 입니다.");
                return "/error.jsp";
            } else {
                User user = new User();
                user.setNickname(nickName);
                user.setPhoneNo(phoneNo);
                user.setPassword(password1);

                txManager.startTransaction();
                userDao.add(user);
                profilePhoto.setRefNo(user.getNo());
                userPhotoDao.add(profilePhoto);
                txManager.commit();
                return "/home.jsp";
            }

        } catch (Exception e) {
            req.setAttribute("message", "회원가입 오류");
            req.setAttribute("exception", e);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            return "/error.jsp";

        }
    }

    @RequestMapping("/auth/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getMethod().equals("GET")) {
            Cookie[] cookies = req.getCookies();
            String savedNickname = "";
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("nickname")) {
                        savedNickname = cookie.getValue();
                        break;
                    }
                }
            }
            req.setAttribute("savedNickname", savedNickname);
            return "/auth/login.jsp";
        }

        String nickName = req.getParameter("nickname");
        String password = req.getParameter("password");
        String checkbox = req.getParameter("saveNickname");

        User loginUser = userDao.findByNicknameAndPassword(nickName, password);
        if (loginUser == null) {
            req.setAttribute("message","닉네임 또는 비밀번호가 일치하지 않습니다.");
            return "/error.jsp";
        } else {
            req.getSession().setAttribute("loginUser", loginUser);
            if (checkbox != null) {
                Cookie cookie = new Cookie("nickname", nickName);
                cookie.setMaxAge(60 * 60 * 24 * 7);
                resp.addCookie(cookie);
            } else {
                Cookie cookie = new Cookie("nickname", "");
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            return "redirect:/app/home";
        }
    }

    @RequestMapping("/auth/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().invalidate();
        return "redirect:/app/home";
    }

    @RequestMapping("/auth/mypage")
    public String mypage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (req.getMethod().equals("GET")) {
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
            return "redirect:/app/auth/mypage";
        } catch (Exception e) {
            req.setAttribute("message", "마이페이지 회원정보 변경 오류");
            req.setAttribute("exception", e);
            return "/error";
        }
    }



    @RequestMapping("/auth/changepw")
    public String changePw(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (req.getMethod().equals("GET")) {
            if (loginUser == null) {
                return "/app/auth/login";
            }
            return "/auth/changepw.jsp";
        }

        try {
            String currPw = req.getParameter("currentpw");
            String newPw1 = req.getParameter("newpw1");
            String newPw2 = req.getParameter("newpw2");


            User user = userDao.findByNicknameAndPassword(loginUser.getNickname(), currPw);
            if (user == null || (loginUser.getNo() != user.getNo()) || !newPw1.equals(newPw2)) {
                req.setAttribute("message","비밀번호가 일치하지 않습니다.");
                return "/error.jsp";
            } else {
                loginUser.setPassword(newPw1);
                userDao.updatePassword(loginUser);
                return "redirect:/app/auth/mypage";
            }
        } catch (Exception e) {
            req.setAttribute("message", "비밀번호 변경 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }

    }


}
