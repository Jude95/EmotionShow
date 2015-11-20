package com.jude.emotionshow.domain.entities;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/5/20.
 */

public class Region implements Serializable{
    private int id;
    private int level;
    private int cid;
    private int parentId;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region(int id, int level, int cid, int parentId, String name) {
        this.id = id;
        this.level = level;
        this.cid = cid;
        this.parentId = parentId;
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Region))return false;
        return getCid()==((Region)o).getCid();
    }

    @Override
    public int hashCode() {
        return getCid();
    }
}
