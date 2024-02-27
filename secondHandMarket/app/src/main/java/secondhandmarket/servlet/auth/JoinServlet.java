package secondhandmarket.servlet.auth;

import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/auth/join")
public class JoinServlet extends HttpServlet {

    UserDaoImpl userDao;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();x
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>중고 장터</title>");
        out.println("</head>");
        out.println("<body>");

        req.getRequestDispatcher("/header").include(req, resp);
        out.println("<form action='/auth/join' method='post'>");
        out.println("<div>");
        out.println("프로필 사진: <input name='photo' type='file'>");
        out.println("</div>");
        out.println("<div>");
        out.println("닉네임: <input name='nickname' type='text'>");
        out.println("</div>");
        out.println("<div>");
        out.println("연락처: <input name='phoneNo' type='text'>");
        out.println("</div>");
        out.println("<div>");
        out.println("비밀번호: <input name='password1' type='password'>");
        out.println("</div>");
        out.println("<div>");
        out.println("비밀번호 확인: <input name='password2' type='password'>");
        out.println("</div>");
        out.println("<button>완료</button>");
        out.println("</form>");
        req.getRequestDispatcher("/footer").include(req, resp);

        out.println("</body>");
        out.println("</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("html/text;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String photo = req.getParameter("photo");
        String nickName = req.getParameter("nickname");
        String phoneNo = req.getParameter("phoneNo");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>중고 장터</title>");
        out.println("</head>");
        out.println("<body>");

        if (!password1.equals(password2)) {
            out.println("<h2>비밀번호가 일치하지 않습니다.</h2>");
            resp.setHeader("Refresh", "1;url=/auth/join");
        } else if (userDao.findBy(nickName).getNickname() != null) {
            out.println("<h2>존재하는 닉네임 입니다.</h2>");
            resp.setHeader("Refresh", "1;url=/auth/join");
        } else {
            out.println("<h2>가입이 완료되었습니다.</h2>");
            User user = new User();
            user.setPhoto(photo);
            user.setNickname(nickName);
            user.setPhoneNo(phoneNo);
            user.setPassword(password1);
            userDao.add(user);
            resp.setHeader("Refresh", "1;url=/home");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
