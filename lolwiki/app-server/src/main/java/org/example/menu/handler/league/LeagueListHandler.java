package org.example.menu.handler.league;

import org.example.dao.GeneralDao;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.League;

import java.util.List;

public class LeagueListHandler implements MenuItemHandler {
    private GeneralDao<League> leagueGeneralDao;

    public LeagueListHandler(GeneralDao<League> leagueGeneralDao) {
        this.leagueGeneralDao = leagueGeneralDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("League Of Legends 프로리그 목록");
        prompt.printf("%-4s\t%-40s\t%s\n", "약칭", "정식 명칭", "지역");
        try {
            List<League> leagues = leagueGeneralDao.findAll();
            for (League league : leagues) {
                prompt.printf("%-4s\t%-50s\t%s\n", league.getName(), league.getFullName(), league.getRegion());
            }
        } catch (Exception e) {
            System.out.println("LeagueListHandler - 목록 오류");
        }
    }
}
