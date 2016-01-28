package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class PersonBrief implements Serializable,Cloneable{
    private int id;
    private String name;
    @SerializedName("face")
    private String avatar;
    private String sign;
    private int type;
    private String street;
    private String realname;
    private int lovestatus;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getLovestatus() {
        return lovestatus;
    }

    public void setLovestatus(int lovestatus) {
        this.lovestatus = lovestatus;
    }
}
