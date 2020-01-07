package com.saibaba.hackathon.Adapters;

public class Model {



    private String name, desc;

    public Model(String username, String desc) {
        this.name = username;
        this.desc = desc;
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
