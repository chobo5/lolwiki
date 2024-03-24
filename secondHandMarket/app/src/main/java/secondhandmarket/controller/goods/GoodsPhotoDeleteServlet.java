package secondhandmarket.controller.goods;

import secondhandmarket.dao.GoodsPhotoDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/goods/delete/photo")
public class GoodsPhotoDeleteServlet extends HttpServlet {
    private GoodsPhotoDaoImpl goodsPhotoDao;
    @Override
    public void init() throws ServletException {
        goodsPhotoDao = (GoodsPhotoDaoImpl) this.getServletContext().getAttribute("goodsPhotoDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int no = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.delete(no);
            req.setAttribute("viewUrl", "redirect:/app/auth/mypage");
        } catch (Exception e) {
            req.setAttribute("message", "상품 사진 삭제 오류");
            req.setAttribute("exception", e);
            req.setAttribute("viewUrl", "/error.jsp");
        }

    }

}
