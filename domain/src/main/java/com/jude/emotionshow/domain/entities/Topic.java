package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Topic {
    private List<Seed> data;
    private String title;
    @SerializedName("typeId")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Seed> getData() {
        return data;
    }

    public void setData(List<Seed> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
