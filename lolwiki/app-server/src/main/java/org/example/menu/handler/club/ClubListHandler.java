package org.example.menu.handler.club;

import org.example.dao.GeneralDao;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.Club;
import org.example.vo.League;

import java.util.List;

public class ClubListHandler implements MenuItemHandler {
    private GeneralDao<Club> clubGeneralDao;

    public ClubListHandler(GeneralDao<Club> clubGeneralDao) {
        this.clubGeneralDao = clubGeneralDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("[구단 목록]");
        prompt.printf("%-5s\t%8s\n", "소속 리그","구단명");
        try {
            List<Club> clubs = clubGeneralDao.findAll();
            for (Club club : clubs) {
                prompt.printf("%-5s\t%8s\n",club.getLeague(), club.getFullName());
            }
        } catch (Exception e) {
            System.out.println("ClubListHandler - 목록 오류");
        }
    }

}
