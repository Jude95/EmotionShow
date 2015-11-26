package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/11/26.
 */
public class ThirdInfo {
    @SerializedName("weibo")
    private String sina;
    @SerializedName("weixin")
    private String wx;
    @SerializedName("qq")
    private String qq;

    public String getSina() {
        return sina;
    }

    public void setSina(String sina) {
        this.sina = sina;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
