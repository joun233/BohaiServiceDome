package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/8 17
 */
public class SignIn extends BmobObject {
    String username;
    String address;
    String date;
    String time;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
