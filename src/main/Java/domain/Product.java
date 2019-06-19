package domain;

import java.security.PublicKey;

public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private int pnum;
    private String imgurl;
    private String description;
    //`id` varchar(100) NOT NULL DEFAULT '',
//        `name` varchar(40) DEFAULT NULL,
//        `price` double DEFAULT NULL,
//        `category` varchar(40) DEFAULT NULL,
//        `pnum` int(11) DEFAULT NULL,
//        `imgurl` varchar(100) DEFAULT NULL,
//        `description` varchar(255) DEFAULT NULL,
//        PRIMARY KEY (`id`)

    public Product(){
        this.id = "test";
        this.name="test";
        this.price=100.0;
        this.category="test";
        this.pnum=100;
        this.imgurl="test";
        this.description="test";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
