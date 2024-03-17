package secondhandmarket.servlet.goods;

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
import java.io.PrintWriter;

@WebServlet("/goods/delete")
public class GoodsDeleteServlet extends HttpServlet {
    GoodsDaoImpl goodsDao;
    GoodsPhotoDaoImpl goodsPhotoDao;
    TransactionManager txManager;

    @Override
    public void init() throws ServletException {
        goodsDao = (GoodsDaoImpl) this.getServletContext().getAttribute("goodsDao");
        goodsPhotoDao = (GoodsPhotoDaoImpl) this.getServletContext().getAttribute("goodsPhotoDao");
        txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                resp.sendRedirect("/auth/login");
                return;
            }
            txManager.startTransaction();
            int goodsNo = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.deleteAll(goodsNo);
            goodsDao.delete(goodsNo);
            txManager.commit();
            resp.sendRedirect("/auth/mypage");
        } catch (Exception e) {
            req.setAttribute("message", "상품 삭제 오류");
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
        }
    }


}
