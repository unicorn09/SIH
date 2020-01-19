package com.saibaba.hackathon.Adapters;

public class ModelNotice {
    String Name,Post,Time,Content,Imageurl;

    public ModelNotice(String name, String post, String time, String content, String imageurl) {
        Name = name;
        Post = post;
        Time = time;
        Content = content;
        Imageurl = imageurl;
    }

    public ModelNotice(String name, String time, String content) {
        Name = name;
        Time = time;
        Content = content;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPost() {
        return Post;
    }

    public void setPost(String post) {
        Post = post;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }
}
