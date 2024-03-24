package secondhandmarket.servlet;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {

    private Log log = LogFactory.getLog(DispatcherServlet.class);


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(req.getPathInfo()).include(req, resp);

        Throwable exception = (Throwable) req.getAttribute("exception");
        if (exception != null) {
            req.setAttribute("message", req.getPathInfo() + " 실행 오류");
            StringWriter stringWriter =  new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            exception.printStackTrace(printWriter);
            req.setAttribute("detail", printWriter.toString());

            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            return;
        }

        List<Cookie> cookies = (List<Cookie>) req.getAttribute("cookies");
        if (cookies != null && cookies.size() > 0) {
            for (Cookie cookie : cookies) {
                resp.addCookie(cookie);
            }
        }

        String viewUrl = (String) req.getAttribute("viewUrl");
        if (viewUrl.startsWith("redirect:")) {
            resp.sendRedirect(viewUrl.substring(9));
        } else {
            req.getRequestDispatcher(viewUrl).forward(req, resp);
        }


    }
}
