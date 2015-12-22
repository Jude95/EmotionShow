package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/11/22.
 */
public class CategoryDetail extends Category {

    @SerializedName("zan")
    private int praiseCount;
    @SerializedName("visit")
    private int visitCount;
    @SerializedName("num")
    private int seedCount;

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
