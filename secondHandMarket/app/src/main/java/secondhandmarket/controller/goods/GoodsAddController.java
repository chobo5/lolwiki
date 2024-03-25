package secondhandmarket.controller.goods;

import secondhandmarket.controller.PageController;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
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
import java.util.UUID;

public class GoodsAddController implements PageController {
    GoodsDaoImpl goodsDao;
    GoodsPhotoDaoImpl goodsPhotoDao;
    String uploadDir;
    TransactionManager txManager;

    public GoodsAddController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, String uploadDir, TransactionManager txManager) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.uploadDir = uploadDir;
        this.txManager = txManager;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getMethod().equals("get")) {
            try {
                User loginUser = (User) req.getSession().getAttribute("loginUser");
                if (loginUser == null) {
                    return "redirect:/app/auth/login";
                }
                return "/goods/add.jsp";
            } catch (Exception e) {
                req.setAttribute("message", "상품 등록페이지 불러오기 오류");
                req.setAttribute("exception", e);
                return "/error.jsp";

            }
        }

        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            String name = req.getParameter("name");
            int price = Integer.parseInt(req.getParameter("price"));
            String spec = req.getParameter("spec");

            Goods goods = new Goods();
            goods.setName(name);
            goods.setPrice(price);
            goods.setSpec(spec);
            goods.setUserNo(loginUser.getNo());
            txManager.startTransaction();
            goodsDao.add(goods);

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
            txManager.commit();
            return "redirect:/app/home";
        } catch (Exception e) {
            req.setAttribute("message", "상품 등록 오류");
            req.setAttribute("exception", e);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            return "/error.jsp";
        }
    }


}
