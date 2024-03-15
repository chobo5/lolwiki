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
            String sql = "INSERT INTO user(nickname, phone_no, password)" +
                    " VALUES(?, ?, sha2(?,256))";
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getPhoneNo());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();

            try (ResultSet keyRs = ps.getGeneratedKeys()) {
                keyRs.next();
                user.setNo(keyRs.getInt(1));
            }

        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 회원 추가 오류");
        }
    }

    public int delete(int no) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "DELETE FROM user WHERE no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, no);
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 회원 삭제 오류");
        }

    }

    public int updateInfo(User user) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "UPDATE user SET nickname = ?," +
                    " phone_no = ?" +
                    " where no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getNickname());
            ps.setString(2, user.getPhoneNo());
            ps.setInt(3, user.getNo());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 회원정보 업데이트 오류");
        }
    }
    public int updatePassword(User user) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "UPDATE user SET" +
                    " password = sha2(?, 256)" +
                    " where no = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getNo());
            return ps.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 회원 비밀번호 업데이트 오류");
        }

    }

    public User findBy(String nickname) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "SELECT no," +
                    " nickname," +
                    " phone_no," +
                    " from user" +
                    " where nickname like ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + nickname + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setNo(rs.getInt("no"));
                user.setNickname(rs.getString("nickname"));
                user.setPhoneNo(rs.getString("phone_no"));
                return user;
            }
            return null;
        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 회원 검색 오류");
        }
    }

    public User findByNicknameAndPassword(String nickname, String password) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "SELECT no," +
                    " nickname," +
                    " phone_no" +
                    " from user" +
                    " where nickname = ?" +
                    " and password = sha2(?, 256)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nickname);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setNo(rs.getInt("no"));
                user.setNickname(rs.getString("nickname"));
                user.setPhoneNo(rs.getString("phone_no"));
                return user;
            }
        return null;
        } catch (Exception e) {
            throw new DaoException("UserDaoImpl - 닉네임,패스워드로 회원 찾기 오류");
        }
    }
}


