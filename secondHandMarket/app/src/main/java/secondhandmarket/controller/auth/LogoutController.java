package secondhandmarket.controller.auth;

import secondhandmarket.controller.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController {
    @RequestMapping
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().invalidate();
        return "redirect:/app/home";
    }
}
