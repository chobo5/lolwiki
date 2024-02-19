package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.Club;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClubDaoImpl implements Dao<Club>{
    DBConnectionPool connectionPool;

    public ClubDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Club club) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO club(foundation," +
                    " belong," +
                    " full_name," +
                    " name," +
                    " leader," +
                    " color," +
                    " league_no)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?);\n";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, club.getFoundation());
            pstmt.setString(2, club.getBelong());
            pstmt.setString(3, club.getFullName());
            pstmt.setString(4, club.getName());
            pstmt.setString(5, club.getLeader());
            pstmt.setString(6, club.getColor());
            pstmt.setString(7, club.getLeagueNo());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public List<Club> findAll() {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select c.full_name," +
                    " l.name" +
                    " from club c, league l" +
                    " where c.league_no = l.league_no" +
                    " order by belong desc";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.getResultSet();
            List<Club> clubs = new ArrayList<>();
            while (rs.next()) {
                Club club = new Club();
                club.setFullName(rs.getString("full_name"));
                club.setLeague(rs.getString("name"));
                clubs.add(club);
            }
            return clubs;
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public Club findBy(int id) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select" +
                    " c.foundation" +
                    " c.full_name," +
                    " c.name," +
                    " c.leader," +
                    " c.color," +
                    " l.name" +
                    " from club c league l" +
                    " where c.league_no = l.league_no" +
                    " and c.club_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.getResultSet();
            if (rs.next()) {
                Club club = new Club();
                club.setFoundation(rs.getDate(1));
                club.setFullName(rs.getString(2));
                club.setName(rs.getString(3));
                club.setLeader(rs.getString(4));
                club.setColor(rs.getString(5));
                club.setLeague(rs.getString(6));
                return club;
            }
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public List<Club> findBy(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "";
            PreparedStatement pstmt = con.prepareStatement(sql);
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int delete(int id) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "";
            PreparedStatement pstmt = con.prepareStatement(sql);
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int delete(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "";
            PreparedStatement pstmt = con.prepareStatement(sql);
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int update(Club club) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "";
            PreparedStatement pstmt = con.prepareStatement(sql);
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }
}
