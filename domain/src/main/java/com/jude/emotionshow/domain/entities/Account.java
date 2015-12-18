package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Account extends PersonDetail  implements Serializable,Cloneable {
    private String token;
    private int gender;
    @SerializedName("rongtoken")
    private String rongYunToken;
    @SerializedName("tel")
    private boolean needTel;

    private int coins;

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isNeedTel() {
        return needTel;
    }

    public void setNeedTel(boolean needTel) {
        this.needTel = needTel;
    }

    public String getRongYunToken() {
        return rongYunToken;
    }

    public void setRongYunToken(String rongYunToken) {
        this.rongYunToken = rongYunToken;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Account clone() {
        Account o = null;
        try {
            o = (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
