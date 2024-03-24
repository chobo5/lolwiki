package secondhandmarket.servlet.goods;

import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
@WebServlet("/goods/list")
public class GoodsListServlet extends HttpServlet {

    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private String uploadDir;

    @Override
    public void init() throws ServletException {
        goodsDao = (GoodsDaoImpl) this.getServletContext().getAttribute("goodsDao");
        goodsPhotoDao = (GoodsPhotoDaoImpl) this.getServletContext().getAttribute("goodsPhotoDao");
        uploadDir = this.getServletContext().getRealPath("/upload/goods");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String keyword = req.getParameter("keyword");
            List<Goods> goodsList = goodsDao.findBy(keyword);
            for (Goods goods : goodsList) {
                goods.setPhotoList(goodsPhotoDao.findBy(goods.getNo()));
            }
            req.setAttribute("goodsList", goodsList);
            req.setAttribute("viewUrl", "/goods/list.jsp");

        } catch (Exception e) {
            req.setAttribute("message", "검색 목록 오류");
            req.setAttribute("exception", e);
            req.setAttribute("viewUrl", "/error.jsp");
        }
    }
}
