package secondhandmarket.controller.auth;

import secondhandmarket.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController implements PageController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().invalidate();
        return "redirect:/app/home";
    }
}
