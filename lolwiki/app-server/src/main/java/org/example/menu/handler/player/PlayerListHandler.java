package org.example.menu.handler.player;

import org.example.dao.GeneralDao;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.Club;
import org.example.vo.Player;

import java.util.List;

public class PlayerListHandler implements MenuItemHandler {
    private GeneralDao<Player> playerGeneralDao;

    public PlayerListHandler(GeneralDao<Player> playerGeneralDao) {
        this.playerGeneralDao = playerGeneralDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("[선수 목록]");
        prompt.printf("%-5s\t%10s\t%8s\t%8s\n", "소속 구단", "아이디", "이름", "포지션");
        try {
            ;
            List<Player> players = playerGeneralDao.findAll();
            System.out.println(players.size());
            for (Player player : players) {
                prompt.printf("%-5s\t%10s\t%8s\t%8s\n",player.getTeam(), player.getGameId(),player.getKorName(), player.getPosition());
            }
        } catch (Exception e) {
            System.out.println("PlayerListHandler - 목록 오류");
        }
    }
}
