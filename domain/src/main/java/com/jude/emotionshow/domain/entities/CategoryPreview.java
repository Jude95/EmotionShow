package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class CategoryPreview {
    @SerializedName("title")
    private Category category;
    private List<Seed> data;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Seed> getData() {
        return data;
    }

    public void setData(List<Seed> data) {
        this.data = data;
    }
}
