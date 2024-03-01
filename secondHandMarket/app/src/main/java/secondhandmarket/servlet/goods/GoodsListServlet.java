package secondhandmarket.servlet.goods;

import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.vo.Goods;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/goods/list")
public class GoodsListServlet extends HttpServlet {

    GoodsDaoImpl goodsDao;

    @Override
    public void init() throws ServletException {
        goodsDao = (GoodsDaoImpl) this.getServletContext().getAttribute("goodsDao");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            String keyword = req.getParameter("keyword");
            List<Goods> goodsList = goodsDao.findBy(keyword);
            if (goodsList.size() > 0) {
                out.println("<table border='1'>");
                out.println("<thead>");
                out.println("<tr> <th>상품명</th> <th>가격</th> <th>등록일</th> </tr>");
                out.println("</thead>");
                out.println("<tbody>");
                for (Goods goods : goodsList) {
                    out.println("<tr>");
                    out.printf("<td>%s</td>\n", goods.getName());
                    out.printf("<td>%s</td>\n", goods.getPrice() + "원");
                    out.printf("<td>%s</td>\n", goods.getRegDate());
                    out.println("</tr>");
                }
                out.println("</tbody>");
                out.println("</table>");
            } else {
                out.println("<h2>등록된 삼품이 없습니다.</h2>");
            }
        } catch (Exception e) {

        }
    }
}
