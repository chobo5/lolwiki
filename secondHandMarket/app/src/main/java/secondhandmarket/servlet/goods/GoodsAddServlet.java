package secondhandmarket.servlet.goods;

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
import java.io.PrintWriter;
import java.util.Collection;
import java.util.UUID;

@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
@WebServlet("/goods/add")
public class GoodsAddServlet extends HttpServlet {
    GoodsDaoImpl goodsDao;
    GoodsPhotoDaoImpl goodsPhotoDao;
    String uploadDir;
    TransactionManager txManager;

    @Override
    public void init() throws ServletException {
        txManager = (TransactionManager) this.getServletContext().getAttribute("txManager");
        goodsDao = (GoodsDaoImpl) this.getServletContext().getAttribute("goodsDao");
        goodsPhotoDao = (GoodsPhotoDaoImpl) this.getServletContext().getAttribute("goodsPhotoDao");
        uploadDir = this.getServletContext().getRealPath("/upload/goods");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                resp.sendRedirect("/auth/login");
                return;
            }
            req.getRequestDispatcher("/goods/add.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("message", "상품 등록페이지 불러오기 오류");
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            resp.sendRedirect("/home");
        } catch (Exception e) {
            req.setAttribute("message", "상품 등록 오류");
            req.setAttribute("exception", e);
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
        }
    }


}
