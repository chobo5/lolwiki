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
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            req.setAttribute("viewUrl","/app/auth/login");
            return;
        }
        req.setAttribute("viewUrl", "/auth/changepw.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            String currPw = req.getParameter("currentpw");
            String newPw1 = req.getParameter("newpw1");
            String newPw2 = req.getParameter("newpw2");

            //입력받은 비밀번호와 HttpSession에 등록된 로그인유저의 닉네임으로 유저를 가져온다.
            User user = userDao.findByNicknameAndPassword(loginUser.getNickname(), currPw);
            //유저가 없거나 loginUser, 가져온유저가 다르면 또는 비밀번호와 확인이 일치하지 않으면 비밀번호가 일치하지 않음 표시
            if (user == null || (loginUser.getNo() != user.getNo()) || !newPw1.equals(newPw2)) {
                req.setAttribute("message","비밀번호가 일치하지 않습니다.");
                req.setAttribute("viewUrl","/error.jsp");
            } else {
                loginUser.setPassword(newPw1);
                userDao.updatePassword(loginUser);
                req.setAttribute("viewUrl","redirect:/app/auth/mypage");
            }
        } catch (Exception e) {
            req.setAttribute("message", "비밀번호 변경 오류");
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }
}
