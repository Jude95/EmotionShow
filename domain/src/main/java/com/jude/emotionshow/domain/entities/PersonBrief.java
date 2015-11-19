package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class PersonBrief implements Serializable{
    private int id;
    private String name;
    @SerializedName("face")
    private String avatar;

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
}
