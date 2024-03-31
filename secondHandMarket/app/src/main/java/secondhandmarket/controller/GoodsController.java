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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    @RequestMapping("/goods/add_form")
    public String addForm(HttpSession session) throws Exception {
        try {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                return "redirect:/app/auth/login_form";
            }
            return "/goods/add_form.jsp";
        } catch (Exception e) {
            throw new Exception("상품 등록페이지 불러오기 오류");

        }
    }

    @RequestMapping("/goods/add")
    public String add(Goods goods,
                      @RequestParam("photos") Part[] photos) throws Exception {
        try {
            txManager.startTransaction();
            goodsDao.add(goods);

            for (Part photo : photos) {
                Photo goodsPhoto = new Photo();
                String filename = UUID.randomUUID().toString();
                photo.write(this.uploadDir + "/" + filename);
                goodsPhoto.setPath(filename);
                goodsPhoto.setRefNo(goods.getNo());
                goodsPhotoDao.add(goodsPhoto);
            }
            txManager.commit();
            return "redirect:/app/home";
        } catch (Exception e) {
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            throw new Exception("상품 등록 오류");
        }
    }


    @RequestMapping("/goods/list")
    public String list(@RequestParam("keyword") String keyword,
                       Map<String, Object> map) throws Exception {
        try {
            List<Goods> goodsList = goodsDao.findBy(keyword);
            for (Goods goods : goodsList) {
                goods.setPhotoList(goodsPhotoDao.findBy(goods.getNo()));
            }
            map.put("goodsList", goodsList);
            return "/goods/list.jsp";

        } catch (Exception e) {
            throw new Exception("검색 목록 오류");
        }
    }

    @RequestMapping("/goods/view")
    public String view(@RequestParam("no") int no,
                       Map<String, Object> map) throws Exception {
        try {
            List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
            Goods goods = goodsDao.findBy(no);
            map.put("goodsPhotos", goodsPhotos);
            map.put("goods", goods);
            return "/goods/view.jsp";
        } catch (Exception e) {
            throw new Exception("상품 상세 불러오기 오류");
        }
    }

    @RequestMapping("/goods/modify_form")
    public String modifyForm(HttpSession session,
                             @RequestParam("no") int no,
                             Map<String, Object> map) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/app/auth/login_form";
        }

        try {
            List<Photo> goodsPhotos = goodsPhotoDao.findBy(no);
            Goods goods = goodsDao.findBy(no);
            map.put("goodsPhotos", goodsPhotos);
            map.put("goods", goods);
            return "/goods/modify_form.jsp";
        } catch (Exception e) {
            throw new Exception("상품 수정 불러오기 오류");
        }


    }

    @RequestMapping("/goods/modify")
    public String modify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/app/auth/login_form";
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
            return "redirect:/app/auth/mypage_form";

        } catch (Exception e) {
            throw new Exception("상품 수정 오류");
        }
    }

    @RequestMapping("/goods/delete")
    public String delete(HttpSession session,
                         @RequestParam("no") int no) throws Exception {
        try {
            User loginUser = (User) session.getAttribute("loginUser");
            if (loginUser == null) {
                return "redirect:/app/auth/login_form";
            }
            txManager.startTransaction();
            goodsPhotoDao.deleteAll(no);
            goodsDao.delete(no);
            txManager.commit();
            return "redirect:/app/auth/mypage_form";
        } catch (Exception e) {
            try {
                txManager.rollback();
            } catch (Exception ex) {
            }
            throw new Exception("상품 삭제 오류");
        }
    }

    @RequestMapping("/goods/delete/photo")
    public String photoDelete(@RequestParam("no") int no) throws Exception {
        try {
            goodsPhotoDao.delete(no);
            return "redirect:/app/auth/mypage_form";
        } catch (Exception e) {
            throw new Exception("상품 사진 삭제 오류");
        }

    }


}
