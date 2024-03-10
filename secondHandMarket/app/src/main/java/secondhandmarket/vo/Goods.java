package secondhandmarket.vo;

import java.sql.Date;
import java.util.List;

public class Goods {
    private int no;
    private String name;
    private int price;
    private String spec;
    private int userNo;
    private Date regDate;

    private List<Photo> photoList;

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", spec='" + spec + '\'' +
                ", userNo=" + userNo +
                ", regDate=" + regDate +
                ", photoList=" + photoList +
                '}';
    }
}
