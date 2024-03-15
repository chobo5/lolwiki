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

@WebServlet("/auth/changepw")
public class ChangePWServlet extends HttpServlet {
    private UserDaoImpl userDao;
    @Override
    public void init() throws ServletException {
        userDao = (UserDaoImpl) this.getServletContext().getAttribute("userDao");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        req.getRequestDispatcher("/header").include(req, resp);
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            resp.sendRedirect("/auth/login");
        }
        out.println("<form action='/auth/changepw' method='post'>");
        out.println("<div>");
        out.println("현재 비밀번호: <input name='currentpw' type='password'>");
        out.println("</div>");
        out.println("<div>");
        out.println("새로운 비밀번호: <input name='newpw1' type='password'>");
        out.println("</div>");
        out.println("<div>");
        out.println("세로운 비밀번호 확인: <input name='newpw2' type='password'>");
        out.println("</div>");
        out.println("<button>확인</button>");
        out.println("</form>");
        req.getRequestDispatcher("/footer").include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            String currPw = req.getParameter("currentpw");
            String newPw1 = req.getParameter("newpw1");
            String newPw2 = req.getParameter("newpw2");

            //입력받은 비밀번호와 HttpSession에 등록된 로그인유저의 닉네임으로 유저를 가져온다.
            User user = userDao.findByNicknameAndPassword(loginUser.getNickname(), currPw);
            //유저가 없거나 loginUser, 가져온유저가 다르면 또는 비밀번호와 확인이 일치하지 않으면 비밀번호가 일치하지 않음 표시
            if (user == null || (loginUser.getNo() != user.getNo()) || !newPw1.equals(newPw2)) {
                out.println("<h2>비밀번호가 일치하지 않습니다.</h2>");
                resp.setHeader("Refresh","1;url=/auth/changepw");
            } else {
                loginUser.setPassword(newPw1);
                userDao.updatePassword(loginUser);
                out.println("<h2>비밀번호가 변경되었습니다.</h2>");
                resp.setHeader("Refresh","1;url=/auth/mypage");
            }




        } catch (Exception e) {

        }
    }


    //    out.println("<div>");
//            out.println("비밀번호: <input name='password1' type='password'>");
//            out.println("</div>");
//            out.println("<div>");
//            out.println("비밀번호 확인: <input name='password2' type='password'>");
//            out.println("</div>");
//String password1 = req.getParameter("password1");
//    String password2 = req.getParameter("password2");
//    if (password1 != null && password1.equals(password2)) {
//        loginUser.setPassword(password1);
//        userDao.updatePassword(loginUser);
//
//    } else {
//
//    }
}
