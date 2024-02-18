package org.example.vo;

public class Club {
    private String foundation;
    private String belongTo;
    private String name;
    private String parentCompany;
    private String ceo;
    private String leader;
    private String color;
    private String league_no;

    public String getFoundation() {
        return foundation;
    }

    public void setFoundation(String foundation) {
        this.foundation = foundation;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
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

    public String getLeague_no() {
        return league_no;
    }

    public void setLeague_no(String league_no) {
        this.league_no = league_no;
    }

    @Override
    public String toString() {
        return "Club{" +
                "foundation='" + foundation + '\'' +
                ", belongTo='" + belongTo + '\'' +
                ", name='" + name + '\'' +
                ", parentCompany='" + parentCompany + '\'' +
                ", ceo='" + ceo + '\'' +
                ", leader='" + leader + '\'' +
                ", color='" + color + '\'' +
                ", league_no='" + league_no + '\'' +
                '}';
    }
}
