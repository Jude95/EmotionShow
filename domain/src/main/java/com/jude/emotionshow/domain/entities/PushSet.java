package com.jude.emotionshow.domain.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuchenxi on 15/12/8.
 */
public class PushSet {
    @SerializedName("zan")
    private boolean isReceivePraiseMessage;
    @SerializedName("comment")
    private boolean isReceiveCommentMessage;
    @SerializedName("community")
    private boolean isReceiveInviteMessage;
    @SerializedName("care")
    private boolean isReceiveFollowMessage;

    public PushSet(boolean isReceiveCommentMessage, boolean isReceiveFollowMessage, boolean isReceiveInviteMessage, boolean isReceivePraiseMessage) {
        this.isReceiveCommentMessage = isReceiveCommentMessage;
        this.isReceiveFollowMessage = isReceiveFollowMessage;
        this.isReceiveInviteMessage = isReceiveInviteMessage;
        this.isReceivePraiseMessage = isReceivePraiseMessage;
    }

    public boolean isReceiveCommentMessage() {
        return isReceiveCommentMessage;
    }

    public void setReceiveCommentMessage(boolean receiveCommentMessage) {
        isReceiveCommentMessage = receiveCommentMessage;
    }

    public boolean isReceiveFollowMessage() {
        return isReceiveFollowMessage;
    }

    public void setReceiveFollowMessage(boolean receiveFollowMessage) {
        isReceiveFollowMessage = receiveFollowMessage;
    }

    public boolean isReceiveInviteMessage() {
        return isReceiveInviteMessage;
    }

    public void setReceiveInviteMessage(boolean receiveInviteMessage) {
        isReceiveInviteMessage = receiveInviteMessage;
    }

    public boolean isReceivePraiseMessage() {
        return isReceivePraiseMessage;
    }

    public void setReceivePraiseMessage(boolean receivePraiseMessage) {
        isReceivePraiseMessage = receivePraiseMessage;
    }
}
