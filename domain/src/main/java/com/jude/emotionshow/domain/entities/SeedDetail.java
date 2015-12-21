package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class SeedDetail extends Seed {
    private String content;
    @SerializedName("ltag")
    private String process;
    @SerializedName("ctag")
    private String scene;
    private String address;
    private long time;
    private String tag;
    private PersonBrief author;
    @SerializedName("commentNum")
    private int commentCount;
    @SerializedName("collect")
    private int collectCount;
    @SerializedName("coins")
    private int score;


    private List<Comment> comment;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public PersonBrief getAuthor() {
        return author;
    }

    public void setAuthor(PersonBrief author) {
        this.author = author;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }
}
