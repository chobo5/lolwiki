package org.example.menu.handler.club;

import org.example.dao.DaoException;
import org.example.dao.GeneralDao;
import org.example.menu.handler.HandlerException;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.Club;
import org.example.vo.League;

import java.sql.Date;

public class ClubAddHandler implements MenuItemHandler {
    private GeneralDao<Club> clubGeneralDao;
    private GeneralDao<League> leagueGeneralDao;

    public ClubAddHandler(GeneralDao<Club> clubGeneralDao, GeneralDao<League> leagueGeneralDao) {
        this.clubGeneralDao = clubGeneralDao;
        this.leagueGeneralDao = leagueGeneralDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("[구단 추가]");
        try {
            Club club = new Club();
            String input = prompt.input("소속리그: ");
            int leagueNo = leagueGeneralDao.getPrimaryKeyNo(input);
            if (leagueNo == -1) {
                throw new HandlerException("리그 번호 찾기 실패");
            }
            club.setLeague(input.toUpperCase());
            club.setFoundation(prompt.inputDate("구단 설립일: "));
            club.setFullName(prompt.input("구단 정식 명칭: "));
            club.setName(prompt.input("구단명:"));
            club.setColor(prompt.input("팀 컬러: "));
            club.setLeagueNo(leagueNo);
            clubGeneralDao.add(club);
        } catch (Exception e) {
            System.out.println("ClubAddHandler - 추가 오류");
        }
    }
}