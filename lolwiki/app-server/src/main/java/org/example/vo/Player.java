package org.example.vo;

import java.sql.Date;

public class Player {
    private int playerNo;
    private String team = "Free";
    private String korName;
    private String engName;
    private String gameId;
    private Date birth;
    private String nationality;
    private Date debut;
    private String position;
    private String korServerId;
    private int roasterNo;

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getKorName() {
        return korName;
    }

    public void setKorName(String korName) {
        this.korName = korName;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getKorServerId() {
        return korServerId;
    }

    public void setKorServerId(String korServerId) {
        this.korServerId = korServerId;
    }

    public int getRoasterNo() {
        return roasterNo;
    }

    public void setRoasterNo(int roasterNo) {
        this.roasterNo = roasterNo;
    }

}
