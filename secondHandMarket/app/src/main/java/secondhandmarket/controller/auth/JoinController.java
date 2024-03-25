package secondhandmarket.controller.auth;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.UserDaoImpl;
import secondhandmarket.dao.UserPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.Collection;
import java.util.UUID;

public class JoinController implements PageController {

    private UserDaoImpl userDao;
    private UserPhotoDaoImpl userPhotoDao;
    private String uploadDir;
    private TransactionManager txManager;

    public JoinController(UserDaoImpl userDao, UserPhotoDaoImpl userPhotoDao, String uploadDir, TransactionManager txManager) {
        this.userDao = userDao;
        this.userPhotoDao = userPhotoDao;
        this.uploadDir = uploadDir;
        this.txManager = txManager;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getMethod().equals("get")) {
            return "/auth/join.jsp";
        }

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
                return "/error.jsp";
            } else if (userDao.findBy(nickName) != null) {
                req.setAttribute("message", "이미 존재하는 닉네임 입니다.");
                return "/error.jsp";
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
                return "/home.jsp";
            }

        } catch (Exception e) {
            req.setAttribute("message", "회원가입 오류");
            req.setAttribute("exception", e);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            return "/error.jsp";

        }
    }
}
