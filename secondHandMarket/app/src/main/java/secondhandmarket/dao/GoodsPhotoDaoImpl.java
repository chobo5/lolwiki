package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class GoodsPhotoDaoImpl {
    DBConnectionPool connectionPool;

    public GoodsPhotoDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Photo photo) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO goods_photo(path, goods_no)" +
                    " VALUES(? , ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, photo.getPath());
            ps.setInt(2, photo.getRefNo());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("GoodsPhotoDaoImpl - 사진 추가 오류");
        }
    }

    public int delete(int no) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "DELETE FROM goods_photo WHERE no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("GoodsPhotoDaoImpl - 사진 삭제 오류");
        }
        return -1;
    }
}
