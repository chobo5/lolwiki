package org.example.vo;

public class League {
    private int leagueNo;
    private String name;
    private String fullName;
    private String foundationYear;
    private String region;
    private String mainAgent;
    private String slogan;
    private String partner;
    private String stadium;
    private String recentChamp;
    private String mostChamp;
    private String mostPlayer;

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

    public String getFoundationYear() {
        return foundationYear;
    }

    public void setFoundationYear(String foundationYear) {
        this.foundationYear = foundationYear;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMainAgent() {
        return mainAgent;
    }

    public void setMainAgent(String mainAgent) {
        this.mainAgent = mainAgent;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getRecentChamp() {
        return recentChamp;
    }

    public void setRecentChamp(String recentChamp) {
        this.recentChamp = recentChamp;
    }

    public String getMostChamp() {
        return mostChamp;
    }

    public void setMostChamp(String mostChamp) {
        this.mostChamp = mostChamp;
    }

    public String getMostPlayer() {
        return mostPlayer;
    }

    public void setMostPlayer(String mostPlayer) {
        this.mostPlayer = mostPlayer;
    }

    @Override
    public String toString() {
        return "League{" +
                "leagueNo=" + leagueNo +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", foundationYear='" + foundationYear + '\'' +
                ", region='" + region + '\'' +
                ", main_agent='" + mainAgent + '\'' +
                ", slogan='" + slogan + '\'' +
                ", stadium='" + stadium + '\'' +
                ", recent_champ='" + recentChamp + '\'' +
                ", most_champ='" + mostChamp + '\'' +
                ", most_player='" + mostPlayer + '\'' +
                '}';
    }
}
