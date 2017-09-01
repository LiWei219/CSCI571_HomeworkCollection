package com.example.myhw9;

/**
 * Created by Administrator on 2017/4/18.
 */

public class obj {
    String name="";
    String picurl = "";
    String id = "";

    public obj(String name, String picurl, String id) {
        this.name = name;
        this.picurl = picurl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
