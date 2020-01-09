package com.saibaba.hackathon.chatbot;

public class ModelChat {

    String text;
    boolean isMe;
    boolean isselected;

    public boolean isIsselected() {
        return isselected;
    }

    public ModelChat(String text, boolean isMe, boolean isselected) {
        this.text = text;
        this.isMe = isMe;
        this.isselected = isselected;
    }
    public boolean getIsselected(){return isselected;}
    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public ModelChat(String text, boolean isMe) {
        this.text = text;
        this.isMe = isMe;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}