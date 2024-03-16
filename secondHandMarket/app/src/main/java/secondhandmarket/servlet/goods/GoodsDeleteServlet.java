package secondhandmarket.servlet.goods;

import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.dao.UserDaoImpl;
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
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                out.println("<h2>로그인이 필요합니다.</h2>");
                resp.setHeader("Refresh", "1;url=/auth/login");
            }
            txManager.startTransaction();
            int goodsNo = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.deleteAll(goodsNo);
            goodsDao.delete(goodsNo);
            txManager.commit();
            out.println("<h3>상품 삭제가 완료되었습니다.</h3>");
            resp.setHeader("Refresh", "1;url=/auth/mypage");
        } catch (Exception e) {
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
        }
    }


}
