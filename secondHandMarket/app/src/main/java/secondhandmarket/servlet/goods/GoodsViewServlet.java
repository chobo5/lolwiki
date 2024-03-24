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
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 2)
@WebServlet("/goods/view")
public class GoodsViewServlet extends HttpServlet {

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
        try {
            int no = Integer.parseInt(req.getParameter("no"));
            List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
            Goods goods = goodsDao.findBy(no);
            req.setAttribute("goodsPhotos", goodsPhotos);
            req.setAttribute("goods", goods);
            req.setAttribute("viewUrl", "/app/goods/view.jsp");
        } catch (Exception e) {
            req.setAttribute("message", "상품 상세 불러오기 오류");
            req.setAttribute("exception", e);
            req.setAttribute("viewUrl", "/error.jsp");
        }
    }
}
