package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements Dao<Player> {
    DBConnectionPool connectionPool;

    public PlayerDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Player player) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO player(kor_name," +
                    " eng_name," +
                    " game_id," +
                    " birth," +
                    " nationality," +
                    " debut," +
                    " position," +
                    " kor_server_id," +
                    " roaster_no)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, player.getKorName());
            pstmt.setString(2, player.getEngName());
            pstmt.setDate(3, player.getBirth());
            pstmt.setString(4, player.getNationality());
            pstmt.setDate(5, player.getDebut());
            pstmt.setString(6, player.getPosition());
            pstmt.setString(7, player.getKorServerId());
            pstmt.setInt(8, player.getRoasterNo());
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new DaoException("선수 추가 오류");
        }
    }

    @Override
    public List<Player> findAll() {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select c.name," +
                    " p.game_id," +
                    " p.kor_name," +
                    " p.kor_server_id," +
                    " p.position" +
                    " from player p, roaster r, season s, club c" +
                    " where p.roaster_no = r.roaster_no" +
                    " and r.season_no = 2024" +
                    " and s.season_no = 2024" +
                    " and s.club_no = c.club_no" +
                    " order by c.name desc";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
                Player player = new Player();
                player.setTeam(rs.getString("name"));
                player.setGameId(rs.getString("game_id"));
                player.setKorName(rs.getString("kor_name"));
                player.setKorName(rs.getString("kor_server_id"));
                player.setKorServerId(rs.getString("position"));
            }
            System.out.println("선수 목록 가져오기 성공");
            return players;

        } catch (Exception e) {
            throw new DaoException("선수 목록 가져오기 오류");
        }

    }

    @Override
    public Player findBy(int id) {
        return null;
    }

    @Override
    public List<Player> findBy(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select game_id," +
                    " kor_name," +
                    " eng_name," +
                    " birth," +
                    " nationality," +
                    " debut," +
                    " position," +
                    " kor_server_id" +
                    " from player" +
                    " where game_id like '%?%'";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.getResultSet();
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
                Player player = new Player();
                player.setGameId(rs.getString("game_id"));
                player.setKorName(rs.getString("kor_name"));
                player.setEngName(rs.getString("eng_name"));
                player.setBirth(rs.getDate("birth"));
                player.setNationality(rs.getString("nationality"));
                player.setDebut(rs.getDate("debut"));
                player.setPosition(rs.getString("position"));
                player.setKorServerId(rs.getString("kor_server_id"));
            }
            System.out.println("선수 목록 가져오기 성공");
            return players;
        } catch (Exception e) {
            throw new DaoException("선수 삭제 오류");
        }
    }

    @Override
    public int delete(int id) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from player where player_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("선수 삭제 오류");
        }
    }

    @Override
    public int delete(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from player where game_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("선수 삭제 오류");
        }
    }

    @Override
    public int update(Player player) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "update player game_id = ?," +
                    " kor_name = ?," +
                    " eng_name = ?," +
                    " birth = ?," +
                    " nationality = ?," +
                    " debut = ?," +
                    " position = ?," +
                    " kor_server_id = ?" +
                    " from player" +
                    " where player_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, player.getGameId());
            pstmt.setString(2, player.getKorName());
            pstmt.setString(3, player.getEngName());
            pstmt.setDate(4, player.getBirth());
            pstmt.setString(5, player.getNationality());
            pstmt.setDate(6, player.getDebut());
            pstmt.setString(7, player.getPosition());
            pstmt.setString(8, player.getKorServerId());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("선수 수정 오류");
        }
    }

}