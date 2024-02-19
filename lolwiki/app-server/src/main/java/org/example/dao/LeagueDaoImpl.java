package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.League;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeagueDaoImpl implements Dao<League> {

    private DBConnectionPool connectionPool;

    public LeagueDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(League league) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "insert into league(full_name," +
                    " name," +
                    " foundation_year," +
                    " region," +
                    " main_agent," +
                    " slogan," +
                    " partner," +
                    " stadium," +
                    " recent_champ," +
                    " most_champ," +
                    " most_player values(? ,? ,? ,? ,? ,? ,? ,? ,?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, league.getFullName());
            pstmt.setString(2, league.getName());
            pstmt.setString(3, league.getFoundationYear());
            pstmt.setString(4, league.getRegion());
            pstmt.setString(5, league.getMainAgent());
            pstmt.setString(6, league.getSlogan());
            pstmt.setString(7, league.getPartner());
            pstmt.setString(8, league.getStadium());
            pstmt.setString(9, league.getRecentChamp());
            pstmt.setString(10, league.getMostChamp());
            pstmt.setString(11, league.getMostPlayer());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("리그 추가 오류");
        }
    }

    @Override
    public List<League> findAll() {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select name, region from league";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.getResultSet();
            List<League> leagues = new ArrayList<>();
            while (rs.next()) {
                League league = new League();
                league.setName(rs.getString("name"));
                league.setRegion(rs.getString("region"));
                leagues.add(league);
            }
            return leagues;
        } catch (Exception e) {
            throw new DaoException("리그 목록 불러오기 오류");
        }
    }

    @Override
    public League findBy(int id) {
        return null;
    }

    @Override
    public List<League> findBy(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select name," +
                    " region," +
                    " where name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.getResultSet();
            List<League> leagues = new ArrayList<>();
            while (rs.next()) {
                League league = new League();
                league.setName(rs.getString("name"));
                league.setRegion(rs.getString("region"));
                leagues.add(league);
            }
            return leagues;
        } catch (Exception e) {
            throw new DaoException("리그 불러오기 오류");
        }
    }

    @Override
    public int delete(int id) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from league where league_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("리그 삭제 오류");
        }
    }

    @Override
    public int delete(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from league where name = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("리그 삭제 오류");
        }
    }

    @Override
    public int update(League league) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "update league full_name = ?," +
                    " name = ?," +
                    " foundation_year = ?," +
                    " region = ?," +
                    " main_agent = ?," +
                    " slogan = ?," +
                    " partner = ?," +
                    " stadium = ?," +
                    " recent_champ = ?," +
                    " most_champ = ?," +
                    " from league = ?" +
                    " where league_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, league.getFullName());
            pstmt.setString(2, league.getName());
            pstmt.setString(3, league.getFoundationYear());
            pstmt.setString(4, league.getRegion());
            pstmt.setString(5, league.getMainAgent());
            pstmt.setString(6, league.getSlogan());
            pstmt.setString(7, league.getPartner());
            pstmt.setString(8, league.getStadium());
            pstmt.setString(9, league.getRecentChamp());
            pstmt.setString(10, league.getMostChamp());
            pstmt.setString(11, league.getMostPlayer());
            pstmt.setInt(12, league.getLeagueNo());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("리그 수정 오류");
        }
    }
}
