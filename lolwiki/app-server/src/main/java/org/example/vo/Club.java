package org.example.vo;

import java.sql.Date;

public class Club {
    private int clubNo;
    private String league;
    private Date foundation;
    private String fullName;
    private String name;
    private String leader;
    private String color;
    private int leagueNo;

    public int getClubNo() {
        return clubNo;
    }

    public void setClubNo(int clubNo) {
        this.clubNo = clubNo;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Date getFoundation() {
        return foundation;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLeagueNo() {
        return leagueNo;
    }

    public void setLeagueNo(int leagueNo) {
        this.leagueNo = leagueNo;
    }

}
