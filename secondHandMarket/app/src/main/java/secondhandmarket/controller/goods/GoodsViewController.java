package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoodsViewController implements PageController {

    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private String uploadDir;

    public GoodsViewController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, String uploadDir) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.uploadDir = uploadDir;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int no = Integer.parseInt(req.getParameter("no"));
            List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
            Goods goods = goodsDao.findBy(no);
            req.setAttribute("goodsPhotos", goodsPhotos);
            req.setAttribute("goods", goods);
            return "/app/goods/view.jsp";
        } catch (Exception e) {
            req.setAttribute("message", "상품 상세 불러오기 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
