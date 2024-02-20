package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.RoasterPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class RoasterPlayerDaoImpl implements RelationshipDao<RoasterPlayer> {
    DBConnectionPool connectionPool;

    public RoasterPlayerDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(RoasterPlayer roasterPlayer) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "insert into roaster_player(roaster_no, player_no, is_captain" +
                    "values(? ,? ,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, roasterPlayer.getRoasterNo());
            pstmt.setInt(2, roasterPlayer.getPlayerNo());
            pstmt.setBoolean(3, roasterPlayer.isCaptain());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("로스터-선수 추가 에러");
        }
    }

    @Override
    public RoasterPlayer findByForeignKey(int foreignKeyId) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select roaster_no, is_captain from roaster_player where player_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, foreignKeyId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                RoasterPlayer roasterPlayer = new RoasterPlayer();
                roasterPlayer.setRoasterNo(rs.getInt("roaster_no"));
                roasterPlayer.setPlayerNo(foreignKeyId);
                roasterPlayer.setCaptain(rs.getBoolean("is_captain"));
                return roasterPlayer;
            }
            return null;
        } catch (Exception e) {
            throw new DaoException("로스터-선수 검색 에러");
        }
    }


    @Override
    public int delete(int id, int foreignKeyId) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from roaster_player" +
                    " where roaster_no == ?" +
                    " and player_no == ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, foreignKeyId);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("로스터-선수 삭제 에러");
        }
    }

    @Override
    public int update(RoasterPlayer roasterPlayer) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "update roaster_player set" +
                    " roaster_no = ?" +
                    " is_captain = ?" +
                    " where player_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, roasterPlayer.getRoasterNo());
            pstmt.setBoolean(2, roasterPlayer.isCaptain());
            pstmt.setInt(3, roasterPlayer.getPlayerNo());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("로스터-선수 업데이트 에러");
        }
    }

}
