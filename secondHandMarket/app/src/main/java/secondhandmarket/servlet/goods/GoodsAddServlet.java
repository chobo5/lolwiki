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
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                req.getRequestDispatcher("/header").include(req, resp);
                out.println("로그인이 필요합니다.");
                req.getRequestDispatcher("/footer").include(req, resp);
                resp.setHeader("Refresh", "1;url=/auth/login");
            }
            req.getRequestDispatcher("/header").include(req, resp);
            out.println("<form action='/goods/add' method='post' enctype='multipart/form-data'>");
            out.println("<div>");
            out.println("상품 사진: <input multiple name='photos' type='file'>");
            out.println("</div>");
            out.println("<div>");
            out.println("상품 이름: <input name='name' type='text'>");
            out.println("</div>");
            out.println("<div>");
            out.println("상품 가격: <input name='price' type='text'>");
            out.println("</div>");
            out.println("<div>");
            out.println("상품 설명: <input name='spec' type='textarea'>");
            out.println("</div>");
            out.println("<button>등록</button>");
            out.println("</form>");
            req.getRequestDispatcher("/footer").include(req, resp);
        } catch (Exception e) {
            System.out.println("GoodsAddServlet get 오류");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        req.getRequestDispatcher("/header").include(req, resp);
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



            out.println("<h2>상품 등록이 완료되었습니다.</h2>");
            resp.setHeader("Refresh", "1;url=/home");
            req.getRequestDispatcher("/footer").include(req, resp);
        } catch (Exception e) {
            System.out.println("GoodsAddServlet - Post 오류");
            try {
                txManager.rollback();
            } catch (Exception ex) {
                System.out.println("GoodsAddServlet - rollback 오류");
            }
        }
    }


}
