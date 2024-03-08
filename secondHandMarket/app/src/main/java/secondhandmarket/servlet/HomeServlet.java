package secondhandmarket.servlet;

import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        req.getRequestDispatcher("/header").include(req, resp);
        User loginUser = (User) req.getSession().getAttribute("loginUser");

        out.println("<br>");
        out.println("<br>");
        if (loginUser != null) {
            out.println("<div>");
            out.println("<a href='/goods/add'>1. 상품 등록하기</a>" );
            out.println("</div>");
        }

        req.getRequestDispatcher("/footer").include(req, resp);
        out.println("</body>");
        out.println("</html>");
    }
}
