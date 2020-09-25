package com.example.bohaiservicedome.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 20
 */
public class LostInformation extends BmobObject implements Serializable {
    private String username;
    private String title;
    private String phoneNum;
    private String desc;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
