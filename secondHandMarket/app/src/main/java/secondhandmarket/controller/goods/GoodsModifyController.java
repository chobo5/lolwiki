package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GoodsModifyController implements PageController {

    private GoodsDaoImpl goodsDao;
    private GoodsPhotoDaoImpl goodsPhotoDao;
    private String uploadDir;

    public GoodsModifyController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, String uploadDir) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.uploadDir = uploadDir;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/app/auth/login";
        }
        if (req.getMethod().equals("get")) {
            try {
                int no = Integer.parseInt(req.getParameter("no"));
                List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
                Goods goods = goodsDao.findBy(no);
                req.setAttribute("goodsPhotos", goodsPhotos);
                req.setAttribute("goods", goods);
                return "/goods/modify.jsp";
            } catch (Exception e) {
                req.setAttribute("message", "상품 수정 불러오기 오류");
                req.setAttribute("exception", e);
                return "/error.jsp";
            }
        }


        try {
            Goods goods = new Goods();
            goods.setNo(Integer.parseInt(req.getParameter("no")));
            goods.setName(req.getParameter("name"));
            goods.setPrice(Integer.parseInt(req.getParameter("price")));
            goods.setSpec(req.getParameter("spec"));

            goodsDao.update(goods);
            Collection<Part> parts = req.getParts();
            for (Part part : parts) {
                if (!part.getName().equals("photos") || part.getSize() == 0) {
                    continue;
                } else {
                    Photo goodsPhoto = new Photo();
                    String filename = UUID.randomUUID().toString();
                    part.write(this.uploadDir + "/" + filename);
                    goodsPhoto.setPath(filename);
                    goodsPhoto.setRefNo(goods.getNo());
                    goodsPhotoDao.add(goodsPhoto);
                }
            }
            return "redirect:/app/auth/mypage";

        } catch (Exception e) {
            req.setAttribute("message", "상품 수정 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }
    }
}
