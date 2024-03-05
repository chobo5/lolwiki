package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserPhotoDaoImpl {
    DBConnectionPool connectionPool;

    public UserPhotoDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Photo photo) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO user_photo(path, user_no)" +
                    " VALUES(? , ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, photo.getPath());
            ps.setInt(2, photo.getRefNo());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("UserPhotoDaoImpl - 사진 추가 오류");
        }
    }

    public int delete(int no) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "DELETE FROM user_photo WHERE no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("UserPhotoDaoImpl - 사진 삭제 오류");
        }
        return -1;
    }
}
