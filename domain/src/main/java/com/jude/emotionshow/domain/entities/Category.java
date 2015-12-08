package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhuchenxi on 15/12/7.
 */
public class Category implements Serializable{
    private int id;
    private String name;
    @SerializedName("name_en")
    private String nameEn;
    @SerializedName("url")
    private String avatar;
    @SerializedName("bg")
    private String background;
    private int type;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
