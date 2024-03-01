package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

//    public List<Goods> findBy(String keyword) {
//        try (Connection con = connectionPool.getConnection()) {
//
//        } catch (Exception e) {
//            System.out.println("GoodsDaoImpl - 키워드 검색 오류");
//        }
//
//    }

//    public List<Goods> findBy(int userNo) {
//        try (Connection con = connectionPool.getConnection()) {
//
//        } catch (Exception e) {
//            System.out.println("GoodsDaoImpl - 사용자로 검색 오류");
//        }
//
//    }
}
