package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class SeedEditable extends Seed {
    @SerializedName("ctag")
    private int scene;
    @SerializedName("ltag")
    private int process;
    private String address;
    @SerializedName("right")
    private int scope;
    @SerializedName("pics")
    private List<Image> pictures;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Image> getPictures() {
        return pictures;
    }

    public void setPictures(List<Image> pictures) {
        this.pictures = pictures;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getScene() {
        return scene;
    }

    public void setScene(int scene) {
        this.scene = scene;
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
