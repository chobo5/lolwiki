package secondhandmarket.servlet.auth;

import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/auth/join")
public class JoinServlet extends HttpServlet {

    private UserDaoImpl userDao;
    private UserPhotoDaoImpl userPhotoDao;
    private String uploadDir;
    private TransactionManager txManager;

    @Override
    public void init() throws ServletException {
        this.userDao = (UserDaoImpl) this.getServletContext().getAttribute("userDao");
        this.userPhotoDao = (UserPhotoDaoImpl) this.getServletContext().getAttribute("userPhotoDao");
        uploadDir = this.getServletContext().getRealPath("/upload/user");
        txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        req.getRequestDispatcher("/header").include(req, resp);
        out.println("<form action='/auth/join' method='post' enctype='multipart/form-data'>");
        out.println("<div>");
        out.println("프로필 사진: <input name='photo' type='file'>");
        out.println("</div>");
        out.println("<div>");
        out.println("닉네임: <input name='nickname' type='text'>");
        out.println("</div>");
        out.println("<div>");
        out.println("연락처: <input name='phoneNo' type='text'>");
        out.println("</div>");
        out.println("<div>");
        out.println("비밀번호: <input name='password1' type='password'>");
        out.println("</div>");
        out.println("<div>");
        out.println("비밀번호 확인: <input name='password2' type='password'>");
        out.println("</div>");
        out.println("<button>완료</button>");
        out.println("</form>");
        req.getRequestDispatcher("/footer").include(req, resp);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String nickName = req.getParameter("nickname");
        String phoneNo = req.getParameter("phoneNo");
        String password1 = req.getParameter("password1");
        String password2 = req.getParameter("password2");


        try {
            Photo profilePhoto = new Photo();
            Collection<Part> parts = req.getParts();
            for (Part part : parts) {
                if (!part.getName().equals("photo") || part.getSize() == 0) {
                    continue;
                } else {
                    String filename = UUID.randomUUID().toString();
                    part.write(this.uploadDir + "/" + filename);
                    profilePhoto.setPath(filename);
                }
            }
            if (profilePhoto.getPath() == null) {

            }

            req.getRequestDispatcher("/header").include(req, resp);


            if (!password1.equals(password2)) {
                out.println("<h2>비밀번호가 일치하지 않습니다.</h2>");
                resp.setHeader("Refresh", "1;url=/auth/join");
            } else if (userDao.findBy(nickName) != null) {
                out.println("<h2>이미 존재하는 닉네임 입니다.</h2>");
                resp.setHeader("Refresh", "1;url=/auth/join");
            } else {
                out.println("<h2>가입이 완료되었습니다.</h2>");
                User user = new User();
                user.setNickname(nickName);
                user.setPhoneNo(phoneNo);
                user.setPassword(password1);
                try {
                    txManager.startTransaction();
                    userDao.add(user);
                    profilePhoto.setRefNo(user.getNo());
                    userPhotoDao.add(profilePhoto);
                    txManager.commit();

                } catch (Exception e) {
                    try {
                        txManager.rollback();
                    } catch (Exception ex) {
                    }
                    throw new RuntimeException(e);
                }

            }
            req.getRequestDispatcher("/footer").include(req, resp);

            resp.setHeader("Refresh", "1;url=/home");
        } catch (Exception e) {

        }


    }
}
