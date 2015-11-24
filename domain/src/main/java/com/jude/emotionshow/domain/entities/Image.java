package com.jude.emotionshow.domain.entities;

/**
 * Created by Mr.Jude on 2015/11/19.
 */
public class Image {
    private String url;
    private int width;
    private int height;

    public Image() {
    }

    public Image(String url, int width, int height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url==null?"http://img.hb.aicdn.com/359072babc85be489bae58ba7dced3835d4b9db13519-J0HYnM_fw658":url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "url:"+url+"     width:"+width+"     height:"+height;
    }
}
