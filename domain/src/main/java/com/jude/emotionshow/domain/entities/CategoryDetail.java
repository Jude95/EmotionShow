package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class CategoryDetail  {
    private int id;
    private int type;
    private String name;
    @SerializedName("bg")
    private String background;
    @SerializedName("zan")
    private int praiseCount;
    @SerializedName("visit")
    private int visitCount;
    @SerializedName("num")
    private int seedCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getSeedCount() {
        return seedCount;
    }

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }
}
