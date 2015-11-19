package com.jude.emotionshow.domain.entities;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Topic {
    private List<Seed> data;
    private String title;

    public List<Seed> getData() {
        return data;
    }

    public void setData(List<Seed> data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
