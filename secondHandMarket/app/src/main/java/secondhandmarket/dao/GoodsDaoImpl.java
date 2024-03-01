package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoodsDaoImpl {

    DBConnectionPool connectionPool;

    public GoodsDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Goods goods) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO goods(name, price, spec, user_no)" +
                    " VALUES(?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, goods.getName());
            ps.setInt(2, goods.getPrice());
            ps.setString(3, goods.getSpec());
            ps.setInt(4, goods.getUserNo());
            ps.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("GoodsDaoImpl - 상품 추가 오류");
        }
    }

    public List<Goods> findBy(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "SELECT" +
                    " name," +
                    " price," +
                    " spec," +
                    " user_no," +
                    " reg_date" +
                    " FROM goods" +
                    " WHERE name like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            List<Goods> list = new ArrayList<>();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setName(rs.getString("name"));
                goods.setPrice(rs.getInt("price"));
                goods.setSpec(rs.getString("spec"));
                goods.setUserNo(rs.getInt("user_no"));
                goods.setRegDate(rs.getDate("reg_date"));
                list.add(goods);
            }
            return list;

        } catch (Exception e) {
            System.out.println("GoodsDaoImpl - 키워드 검색 오류");
        }
        return null;
    }

//    public List<Goods> findBy(int userNo) {
//        try (Connection con = connectionPool.getConnection()) {
//
//        } catch (Exception e) {
//            System.out.println("GoodsDaoImpl - 사용자로 검색 오류");
//        }
//
//    }
}
