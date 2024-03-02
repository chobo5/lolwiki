package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PhotoDaoImpl {
    DBConnectionPool connectionPool;

    public PhotoDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(Photo photo) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO photo(path, ref_no)" +
                    " VALUES(? , ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, photo.getPath());
            ps.setInt(2, photo.getRefNo());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("PhotoDaoImpl - 사진 추가 오류");
        }
    }

    public int delete(int no) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "DELETE FROM photo where no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("PhotoDaoImpl - 사진 삭제 오류");
        }
        return -1;
    }
}
