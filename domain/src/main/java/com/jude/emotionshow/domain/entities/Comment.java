package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mr.Jude on 2015/11/21.
 */
public class Comment  {
    private int id;
    @SerializedName("hid")
    private int seedId;
    @SerializedName("uid")
    private int authorId;
    private String content;
    private long time;
    @SerializedName("name")
    private String authorName;
    @SerializedName("face")
    private String authorAvatar;

    @SerializedName("fid")
    private int originalId;
    @SerializedName("reply")
    private String originalAuthorName;
    @SerializedName("replyId")
    private int originalAuthorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeedId() {
        return seedId;
    }

    public void setSeedId(int seedId) {
        this.seedId = seedId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getOriginalAuthorName() {
        return originalAuthorName;
    }

    public void setOriginalAuthorName(String originalAuthorName) {
        this.originalAuthorName = originalAuthorName;
    }

    public int getOriginalAuthorId() {
        return originalAuthorId;
    }

    public void setOriginalAuthorId(int originalAuthorId) {
        this.originalAuthorId = originalAuthorId;
    }
}
