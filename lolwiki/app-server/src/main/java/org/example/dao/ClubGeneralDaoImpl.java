package org.example.dao;

import org.example.util.DBConnectionPool;
import org.example.vo.Club;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClubGeneralDaoImpl implements GeneralDao<Club> {
    DBConnectionPool connectionPool;

    public ClubGeneralDaoImpl(DBConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public void add(Club club) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "INSERT INTO club(foundation," +
                    " full_name," +
                    " name," +
                    " leader," +
                    " color," +
                    " league_no)" +
                    "VALUES(?, ?, ?, ?, ?, ?);\n";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, club.getFoundation());
            pstmt.setString(2, club.getFullName());
            pstmt.setString(3, club.getName());
            pstmt.setString(4, club.getLeader());
            pstmt.setString(5, club.getColor());
            pstmt.setInt(6, club.getLeagueNo());
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
                    " order by c.league_no desc";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<Club> clubs = new ArrayList<>();
            while (rs.next()) {
                Club club = new Club();
                club.setFullName(rs.getString("full_name"));
                club.setLeague(rs.getString("name"));
                clubs.add(club);
            }
            return clubs;
        } catch (Exception e) {
            throw new DaoException("구단 목록 오류");
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
            ResultSet rs = pstmt.executeQuery();
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
            return null;
        } catch (Exception e) {
            throw new DaoException("구단 검색 오류");
        }
    }

    @Override
    public List<Club> findBy(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "select" +
                    " l.name" +
                    " c.name" +
                    " c.full_name," +
                    " from club c league l" +
                    " where c.league_no = l.league_no" +
                    " and c.name like '%?%'";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            ResultSet rs = pstmt.executeQuery();
            List<Club> clubs = new ArrayList<>();
            while (rs.next()) {
                Club club = new Club();
                club.setLeague(rs.getString(1));
                club.setName(rs.getString(2));
                club.setFullName(rs.getString(3));
                clubs.add(club);
            }
            return clubs;
        } catch (Exception e) {
            throw new DaoException("구단 검색 오류");
        }
    }

    @Override
    public int delete(int id) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from club where club_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int delete(String keyword) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "delete from club where club_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, keyword);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }

    @Override
    public int update(Club club) {
        try (Connection con = connectionPool.getConnection()) {
            String sql = "update club set" +
                    " foundation = ?," +
                    " full_name = ?," +
                    " name = ?," +
                    " leader = ?," +
                    " color = ?," +
                    " league_no = ?" +
                    " where club_no = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setDate(1, club.getFoundation());
            pstmt.setString(2, club.getFullName());
            pstmt.setString(3, club.getName());
            pstmt.setString(4, club.getLeader());
            pstmt.setString(5, club.getColor());
            pstmt.setInt(6, club.getLeagueNo());
            pstmt.setInt(7, club.getClubNo());
            return pstmt.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("구단 추가 오류");
        }
    }
}
