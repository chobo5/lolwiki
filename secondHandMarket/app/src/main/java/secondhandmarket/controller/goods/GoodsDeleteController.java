package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoodsDeleteController implements PageController {
    GoodsDaoImpl goodsDao;
    GoodsPhotoDaoImpl goodsPhotoDao;
    TransactionManager txManager;

    public GoodsDeleteController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, TransactionManager txManager) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.txManager = txManager;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                return "redirect:/app/auth/login";
            }
            txManager.startTransaction();
            int goodsNo = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.deleteAll(goodsNo);
            goodsDao.delete(goodsNo);
            txManager.commit();
            return "redirect:/app/auth/mypage";
        } catch (Exception e) {
            req.setAttribute("message", "상품 삭제 오류");
            req.setAttribute("exception", e);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            return "/error.jsp";
        }
    }


}
