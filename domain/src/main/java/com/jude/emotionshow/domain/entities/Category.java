package com.jude.emotionshow.domain.entities;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Category {
    private Title title;
    public static class Title{
        private String ch;
        private String en;
        private String url;

        public String getCh() {
            return ch;
        }

        public void setCh(String ch) {
            this.ch = ch;
        }

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    private List<Seed> data;

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public List<Seed> getData() {
        return data;
    }

    public void setData(List<Seed> data) {
        this.data = data;
    }
}
