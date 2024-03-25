package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsPhotoDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoodsPhotoDeleteController implements PageController {
    private GoodsPhotoDaoImpl goodsPhotoDao;

    public GoodsPhotoDeleteController(GoodsPhotoDaoImpl goodsPhotoDao) {
        this.goodsPhotoDao = goodsPhotoDao;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int no = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.delete(no);
            return "redirect:/app/auth/mypage";
        } catch (Exception e) {
            req.setAttribute("message", "상품 사진 삭제 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }

    }

}
