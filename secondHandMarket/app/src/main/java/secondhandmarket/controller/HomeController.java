package secondhandmarket.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HomeController {

    @RequestMapping("/home")
    public String home() {
        return "/home.jsp";
    }
}
