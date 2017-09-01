package com.example.myhw9;

/**
 * Created by Administrator on 2017/4/23.
 */

public class postitem {
    String picurl="";
    String name = "";
    String time = "";
    String message = "";
    String story = "";


    public postitem(String name,String picurl, String time, String message,String story) {
        this.picurl = picurl;
        this.name = name;
        this.time = time;
        this.message = message;
        this.story = story;
    }

    public String getPicurl() {
        return picurl;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getStory() {
        return story;
    }
}
