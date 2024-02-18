package org.example.vo;

public class League {
    private int leagueNo;
    private String name;
    private String fullName;
    private String foundationDate;
    private String region;
    private String main_agent;
    private String slogan;
    private String stadium;
    private String recent_champ;
    private String most_champ;
    private String most_player;

    public int getLeagueNo() {
        return leagueNo;
    }

    public void setLeagueNo(int leagueNo) {
        this.leagueNo = leagueNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(String foundationDate) {
        this.foundationDate = foundationDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMain_agent() {
        return main_agent;
    }

    public void setMain_agent(String main_agent) {
        this.main_agent = main_agent;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getRecent_champ() {
        return recent_champ;
    }

    public void setRecent_champ(String recent_champ) {
        this.recent_champ = recent_champ;
    }

    public String getMost_champ() {
        return most_champ;
    }

    public void setMost_champ(String most_champ) {
        this.most_champ = most_champ;
    }

    public String getMost_player() {
        return most_player;
    }

    public void setMost_player(String most_player) {
        this.most_player = most_player;
    }

    @Override
    public String toString() {
        return "League{" +
                "leagueNo=" + leagueNo +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", foundationDate='" + foundationDate + '\'' +
                ", region='" + region + '\'' +
                ", main_agent='" + main_agent + '\'' +
                ", slogan='" + slogan + '\'' +
                ", stadium='" + stadium + '\'' +
                ", recent_champ='" + recent_champ + '\'' +
                ", most_champ='" + most_champ + '\'' +
                ", most_player='" + most_player + '\'' +
                '}';
    }
}
