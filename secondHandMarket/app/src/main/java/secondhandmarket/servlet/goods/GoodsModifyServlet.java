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
@WebServlet("/goods/modify")
public class GoodsModifyServlet extends HttpServlet {

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
        int no = Integer.parseInt(req.getParameter("no"));
        List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
        Goods goods = goodsDao.findBy(no);
        req.getRequestDispatcher("/header").include(req, resp);

        for (Photo goodsPhoto : goodsPhotos) {
            out.printf("<img src='/upload/goods/%s' width=250 height=250>\n", goodsPhoto.getPath());
        }
        out.println("<form action='/goods/modify' method='post' enctype='multipart/form-data'>");
        out.println("<div>");
        out.println("<h3>상품명</h3>");
        out.printf("<input name='name' value='%s'>\n", goods.getName());
        out.println("</div>");
        out.println("<div>");
        out.println("<h3>가격</h3>");
        out.printf("<input name='price' value='%s'>\n", goods.getPrice());
        out.println("</div>");
        out.println("<div>");
        out.println("<h3>설명</h3>");
        out.printf("<input name='spec' value='%s'>\n", goods.getSpec());
        out.println("</div>");
        out.println("<button>변경</button>");
        out.println("</form>");
        out.println("<a href='/goods/delete'>삭제</a>");



        req.getRequestDispatcher("/footer").include(req, resp);
    }


}
