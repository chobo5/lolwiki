package secondhandmarket.dao;

import secondhandmarket.util.DBConnectionPool;
import secondhandmarket.vo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl {
    DBConnectionPool connectionPool;

    public UserDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void add(User user) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO user(nickname, phone_no, password, photo)" +
                    " VALUES(?, ?, sha2(?,256), ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getPhoneNo());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoto());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("MemberDaoImpl - 회원 추가 오류");
        }
    }

    public int delete(int no) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "DELETE FROM user WHERE no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("MemberDaoImpl - 회원 삭제 오류");
        }
        return -1;
    }

    public int update(User user) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "UPDATE user SET nickname = ?," +
                    " phone_no = ?," +
                    " password = ?," +
                    " photo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getPhoneNo());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoto());
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("MemberDaoImpl - 회원 업데이트 오류");
        }
        return -1;
    }

    public User findBy(String nickname) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "SELECT no," +
                    " nickname," +
                    " phone_no," +
                    " photo" +
                    " from user" +
                    " where nick name like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nickname + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setNo(rs.getInt("no"));
                user.setNickname(rs.getString("nickname"));
                user.setPhoneNo(rs.getString("phone_no"));
                user.setPhoto(rs.getString("photo"));
                return user;
            }

        } catch (Exception e) {
            System.out.println("MemberDaoImpl - 회원 검색 오류");
        }
        return null;
    }
}


