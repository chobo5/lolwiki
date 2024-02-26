package org.example.menu.handler.player;

import org.example.dao.ClubGeneralDaoImpl;
import org.example.dao.GeneralDao;
import org.example.dao.RelationshipDao;
import org.example.menu.handler.HandlerException;
import org.example.menu.handler.MenuItemHandler;
import org.example.util.Prompt;
import org.example.vo.Club;
import org.example.vo.Player;
import org.example.vo.Roaster;
import org.example.vo.RoasterPlayer;

import java.util.List;

public class PlayerAddHandler implements MenuItemHandler {
    private GeneralDao<Player> playerGeneralDao;
    private GeneralDao<Club> clubGeneralDao;

    private RelationshipDao<RoasterPlayer> roasterPlayerDao;
    private GeneralDao<Roaster> roasterDao;

    public PlayerAddHandler(GeneralDao<Player> playerGeneralDao, GeneralDao<Club> clubGeneralDao,
                            RelationshipDao<RoasterPlayer> roasterPlayerDao, GeneralDao<Roaster> roasterDao) {
        this.playerGeneralDao = playerGeneralDao;
        this.clubGeneralDao = clubGeneralDao;
        this.roasterPlayerDao = roasterPlayerDao;
        this.roasterDao = roasterDao;
    }

    @Override
    public void action(Prompt prompt) {
        prompt.println("[선수 추가]");

        try {
            Player player = new Player();
            String input = prompt.input("소속 구단: ");
            int clubNo = ((ClubGeneralDaoImpl) clubGeneralDao).findByWord(input);
            if (clubNo == 0) {
                throw new HandlerException("구단 번호 찾기 실패");
            }
            player.setKorName(prompt.input("선수 이름: "));
            player.setEngName(prompt.input("선수 이름(영문): "));
            player.setGameId(prompt.input("선수 아이디:"));
            player.setBirth(prompt.inputDate("선수 생년월일: "));
            player.setNationality(prompt.input("국적: "));
            player.setDebut(prompt.inputDate("데뷔: "));
            player.setPosition(prompt.input("포지션: "));
            player.setKorServerId(prompt.input("한국 서버 아이디: "));
            playerGeneralDao.add(player);



            Roaster roaster = new Roaster();
            int roasterNo = roasterDao.findBy(clubNo).getRoasterNo();

            int playerNo = playerGeneralDao.findBy(player.getGameId()).get(0).getPlayerNo();
            RoasterPlayer rp = new RoasterPlayer();
            rp.setPlayerNo(playerNo);
            rp.setRoasterNo(roasterNo);
            roasterPlayerDao.add(rp);

            player.setTeam(input.toUpperCase());
        } catch (Exception e) {
            System.out.println("PlayerAddHandler - 추가 오류");
        }
    }
}
