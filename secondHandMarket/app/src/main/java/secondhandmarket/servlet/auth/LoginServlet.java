package secondhandmarket.servlet.auth;

import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    UserDaoImpl userDao;

    @Override
    public void init() throws ServletException {
        this.userDao = (UserDaoImpl) this.getServletContext().getAttribute("userDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.setAttribute("viewUrl", "/auth/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickName = req.getParameter("nickname");
        String password = req.getParameter("password");
        String checkbox = req.getParameter("saveNickname");

        User loginUser = userDao.findByNicknameAndPassword(nickName, password);
        if (loginUser == null) {
            req.setAttribute("message","닉네임 또는 비밀번호가 일치하지 않습니다.");
            req.setAttribute("viewUrl", "/error.jsp");
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
            req.setAttribute("viewUrl", "redirect:/app/home");
        }
    }
}
