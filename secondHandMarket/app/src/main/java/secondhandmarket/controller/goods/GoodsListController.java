package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.vo.Goods;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GoodsListController implements PageController {

    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private String uploadDir;

    public GoodsListController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, String uploadDir) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.uploadDir = uploadDir;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String keyword = req.getParameter("keyword");
            List<Goods> goodsList = goodsDao.findBy(keyword);
            for (Goods goods : goodsList) {
                goods.setPhotoList(goodsPhotoDao.findBy(goods.getNo()));
            }
            req.setAttribute("goodsList", goodsList);
            return "/goods/list.jsp";

        } catch (Exception e) {
            req.setAttribute("message", "검색 목록 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
