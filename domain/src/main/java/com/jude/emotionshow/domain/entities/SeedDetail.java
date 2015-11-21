package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class SeedDetail extends Seed {
    @SerializedName("ctag")
    private String scene;
    @SerializedName("ltag")
    private String process;
    private String address;
    @SerializedName("right")
    private int scope;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }
}
