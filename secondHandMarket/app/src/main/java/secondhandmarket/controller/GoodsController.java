package secondhandmarket.controller;

import secondhandmarket.controller.RequestMapping;
import secondhandmarket.dao.GoodsDaoImpl;
import secondhandmarket.dao.GoodsPhotoDaoImpl;
import secondhandmarket.util.TransactionManager;
import secondhandmarket.vo.Goods;
import secondhandmarket.vo.Photo;
import secondhandmarket.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class GoodsController {
    GoodsDaoImpl goodsDao;
    GoodsPhotoDaoImpl goodsPhotoDao;
    String uploadDir;
    TransactionManager txManager;

    public GoodsController(GoodsDaoImpl goodsDao, GoodsPhotoDaoImpl goodsPhotoDao, String uploadDir, TransactionManager txManager) {
        this.goodsDao = goodsDao;
        this.goodsPhotoDao = goodsPhotoDao;
        this.uploadDir = uploadDir;
        this.txManager = txManager;
    }
    @RequestMapping("/goods/add")
    public String add(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (req.getMethod().equals("GET")) {
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


    @RequestMapping("/goods/list")
    public String list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
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

    @RequestMapping("/goods/view")
    public String view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int no = Integer.parseInt(req.getParameter("no"));
            List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
            Goods goods = goodsDao.findBy(no);
            req.setAttribute("goodsPhotos", goodsPhotos);
            req.setAttribute("goods", goods);
            return "/goods/view.jsp";
        } catch (Exception e) {
            req.setAttribute("message", "상품 상세 불러오기 오류");
            req.setAttribute("exception", e);
            return "/error.jsp";
        }
    }

    @RequestMapping("/goods/modify")
    public String modify(HttpServletRequest req, HttpServletResponse resp) throws Exception{
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/app/auth/login";
        }
        if (req.getMethod().equals("GET")) {
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

    @RequestMapping("/goods/delete")
    public String delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            User loginUser = (User) req.getSession().getAttribute("loginUser");
            if (loginUser == null) {
                return "redirect:/app/auth/login";
            }
            txManager.startTransaction();
            int goodsNo = Integer.parseInt(req.getParameter("no"));
            goodsPhotoDao.deleteAll(goodsNo);
            goodsDao.delete(goodsNo);
            txManager.commit();
            return "redirect:/app/auth/mypage";
        } catch (Exception e) {
            req.setAttribute("message", "상품 삭제 오류");
            req.setAttribute("exception", e);
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            return "/error.jsp";
        }
    }

    @RequestMapping("/goods/delete/photo")
    public String photoDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
