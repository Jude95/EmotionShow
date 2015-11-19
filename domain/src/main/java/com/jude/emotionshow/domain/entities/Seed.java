package com.jude.emotionshow.domain.entities;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Seed {
    private int id;
    private int uid;
    private String name;
    private String face;
    private List<Image> pics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public List<Image> getPics() {
        return pics;
    }

    public void setPics(List<Image> pics) {
        this.pics = pics;
    }
}
