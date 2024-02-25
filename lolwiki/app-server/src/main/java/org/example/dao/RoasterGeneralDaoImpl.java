package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.Club;
import org.example.vo.Roaster;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoasterGeneralDaoImpl implements GeneralDao<Roaster>{
    DBConnectionPool connectionPool;

    public RoasterGeneralDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Roaster roaster) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO roaster(roaster_no," +
                    " club_no," +
                    " season," +
                    " director," +
                    " coach" +
                    "VALUES(?, ?, ?, ?, ?);\n";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, roaster.getRoasterNo());
            pstmt.setInt(2, roaster.getClubNo());
            pstmt.setInt(3, roaster.getSeason());
            pstmt.setString(4, roaster.getDirector());
            pstmt.setString(5, roaster.getCoach());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("로스터 추가 오류");
        }
    }

    @Override
    public List<Roaster> findAll() {
       return null;
    }

    @Override
    public Roaster findBy(int season) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select" +
                    " roaster_no" +
                    " director" +
                    " coach" +
                    " from roaster" +
                    " where  season = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, season);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Roaster roaster = new Roaster();
                roaster.setSeason(rs.getInt(1));
                roaster.setDirector(rs.getString(2));
                roaster.setCoach(rs.getString(3));
                return roaster;
            }
            return null;
        } catch (Exception e) {
            throw new DaoException("구단 검색 오류");
        }
    }

    public List<Roaster> findBy(Club club) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select r.season," +
                    " r.director" +
                    " r.coach" +
                    " from roaster r, club c" +
                    " where c.club_no = ?" +
                    "and c.club_no = r.club_no" +
                    " order by r.season desc";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, club.getClubNo());
            ResultSet rs = pstmt.executeQuery();
            List<Roaster> roasters = new ArrayList<>();
            while (rs.next()) {
                Roaster roaster = new Roaster();
                roaster.setSeason(rs.getInt("season"));
                roaster.setDirector(rs.getString("director"));
                roaster.setCoach(rs.getString("coach"));
            }
            return roasters;
        } catch (Exception e) {
            throw new DaoException("로스터 목록 오류");
        }
    }

    @Override
    public List<Roaster> findBy(String keyword) {
        return null;
    }

    @Override
    public int delete(int season) {
        try (Connection con = connectionPool.getConnection()) {
//            String sql = "delete from roaster where season = ?";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setInt(1, season);
//            return pstmt.executeUpdate();
            return 0;
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int delete(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
//            String sql = "delete from club where club_no = ?";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setString(1, keyword);
//            return pstmt.executeUpdate();
            return 0;
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int update(Roaster roaster) {
        try (Connection con = connectionPool.getConnection()) {
//            String sql = "update club set" +
//                    " foundation = ?," +
//                    " full_name = ?," +
//                    " name = ?," +
//                    " color = ?," +
//                    " league_no = ?" +
//                    " where club_no = ?";
//            PreparedStatement pstmt = con.prepareStatement(sql);
//            pstmt.setDate(1, club.getFoundation());
//            pstmt.setString(2, club.getFullName());
//            pstmt.setString(3, club.getName());
//            pstmt.setString(4, club.getLeader());
//            pstmt.setString(5, club.getColor());
//            pstmt.setInt(6, club.getLeagueNo());
//            pstmt.setInt(7, club.getClubNo());
//            return pstmt.executeUpdate();
            return 0;
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }
}
