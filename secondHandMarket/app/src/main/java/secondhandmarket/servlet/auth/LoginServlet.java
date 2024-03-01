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
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
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
        req.getRequestDispatcher("/header").include(req, resp);
        out.println("<form action='/auth/login' method='post'>");
        out.println("<div>");
        out.printf("닉네임: <input name='nickname' type='text' value='%s'>\n", savedNickname);
        out.println("</div>");
        out.println("<div>");
        out.println("비밀번호: <input name='password' type='password'>");
        out.println("</div>");
        out.println("<div>");
        out.println("닉네임 저장: <input name='saveNickname' type='checkbox'>");
        out.println("</div>");
        out.println("<button>로그인</button>");
        out.println("</form>");
        req.getRequestDispatcher("/footer").include(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String nickName = req.getParameter("nickname");
        String password = req.getParameter("password");
        String checkbox = req.getParameter("saveNickname");

        req.getRequestDispatcher("/header").include(req, resp);

        User loginUser = userDao.findByNicknameAndPassword(nickName, password);
        if (loginUser == null) {
            out.println("<h2>닉네임 또는 비밀번호가 일치하지 않습니다.</h2>");
            resp.setHeader("Refresh", "1;url=/auth/login");
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
            resp.sendRedirect("/home");
            return;
        }

        req.getRequestDispatcher("/footer").include(req, resp);

        out.println("</body>");
        out.println("</html>");
    }
}
