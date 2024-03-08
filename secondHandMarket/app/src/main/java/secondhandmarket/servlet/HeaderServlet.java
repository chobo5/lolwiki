package secondhandmarket.servlet;

import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/header")
public class HeaderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String nickname = "";
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser != null) {
            nickname = loginUser.getNickname();
        }
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>중고 장터</a></title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1><a href='/home'>중고 장터</a></h1>");
        out.println("<form action='/goods/list'");
        out.println("<div>");
        out.println("<input name='keyword' type='text' placeholder='검색어를 입력해주세요'>" );
        out.println("</div>");
        out.println("<button>검색</button>");
        out.println("</form>");
        if (loginUser == null) {
            out.println("<a href='/auth/join'>회원가입</a>");
            out.println("<a href='/auth/login'>로그인</a>");
        } else {
            out.printf("<span>%s</span>\n", nickname);
            out.println("<a href='/auth/logout'>로그아웃</a>");
        }


    }
}
