package org.example.menu.handler.league;

import org.example.dao.GeneralDao;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.League;

import java.util.List;

public class LeagueAddHandler implements MenuItemHandler {
    private GeneralDao<League> leagueGeneralDao;

    public LeagueAddHandler(GeneralDao<League> leagueGeneralDao) {
        this.leagueGeneralDao = leagueGeneralDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("[프로리그 추가]");
        try {
            League league = new League();
            league.setName(prompt.input("리그 약칭: "));
            league.setFullName(prompt.input("리그 정식 명칭: "));
            league.setRegion(prompt.input("리그 지역: "));
            league.setStadium(prompt.input("경기장:"));
            league.setFoundationYear(prompt.input("출범 년도: "));
            league.setMainAgent(prompt.input("운영 주체: "));
            league.setPartner(prompt.input("공식 파트너: "));
            league.setSlogan(prompt.input("표어: "));
            league.setRecentChamp(prompt.input("최근 우승팀: "));
            league.setMostChamp(prompt.input("최다 우승팀: "));
            league.setMostPlayer(prompt.input("최다 우승자: "));
            leagueGeneralDao.add(league);
        } catch (Exception e) {
            System.out.println("LeagueAddHandler - 추가 오류");
        }
    }
}
