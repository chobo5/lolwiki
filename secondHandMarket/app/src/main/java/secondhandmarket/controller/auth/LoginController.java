package secondhandmarket.controller.auth;

import secondhandmarket.controller.RequestMapping;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.vo.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController {

    UserDaoImpl userDao;

    public LoginController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @RequestMapping
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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
}
