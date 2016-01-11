package com.jude.emotionshow.domain.entities;

import java.io.Serializable;

/**
 * Created by mike on 2015/12/30.
 */
public class Order implements Serializable {
    private int id;
    private int uid;
    private int gid;
    private String price;
    private int num;
    private String info;
    private String des;
    private String pic;
    private String goodsName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", uid=" + uid +
                ", gid=" + gid +
                ", price='" + price + '\'' +
                ", num=" + num +
                ", info='" + info + '\'' +
                ", des='" + des + '\'' +
                ", pic='" + pic + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }
}
