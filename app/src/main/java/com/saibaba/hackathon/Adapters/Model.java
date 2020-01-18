package com.saibaba.hackathon.Adapters;

import android.graphics.drawable.Drawable;

public class Model {
    private String name, desc;
    private Drawable img;
    public Model(String username, String desc) {
        this.name = username;
        this.desc = desc;
    }

    public Model(String name, String desc, Drawable img) {
        this.name = name;
        this.desc = desc;
        this.img = img;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



}
