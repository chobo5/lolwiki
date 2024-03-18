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
        req.setAttribute("viewUrl", "/auth/join.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

            if (!password1.equals(password2)) {
                req.setAttribute("message", "비밀번호가 일치하지 않습니다.");
                req.setAttribute("viewUrl", "/error.jsp");
            } else if (userDao.findBy(nickName) != null) {
                req.setAttribute("message", "이미 존재하는 닉네임 입니다.");
                req.setAttribute("viewUrl", "/error.jsp");
            } else {
                User user = new User();
                user.setNickname(nickName);
                user.setPhoneNo(phoneNo);
                user.setPassword(password1);

                txManager.startTransaction();
                userDao.add(user);
                profilePhoto.setRefNo(user.getNo());
                userPhotoDao.add(profilePhoto);
                txManager.commit();
                req.setAttribute("viewUrl", "/home.jsp");
            }

        } catch (Exception e) {
            req.setAttribute("message", "회원가입 오류");
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
        }
    }
}
