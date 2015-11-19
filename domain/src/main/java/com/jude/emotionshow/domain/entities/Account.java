package com.jude.emotionshow.domain.entities;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Account extends PersonDetail  implements Serializable {
    private String token;
    private int gender;

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
}
