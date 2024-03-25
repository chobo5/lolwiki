package secondhandmarket.controller.auth;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePwController implements PageController {
    UserDaoImpl userDao;

    public ChangePwController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (req.getMethod().equals("get")) {
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
