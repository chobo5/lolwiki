package secondhandmarket.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PageController {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
