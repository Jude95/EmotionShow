package com.jude.emotionshow.domain.entities;

/**
 * Created by mike on 2015/12/29.
 */
public class Pay {
    private String info;
    private String coins;
    private long time;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
