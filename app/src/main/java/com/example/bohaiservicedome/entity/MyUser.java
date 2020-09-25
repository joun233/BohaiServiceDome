package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author: created by JiangNan
 * @Date: 2020/3/31 16
 */
public class MyUser extends BmobUser {
    private String fullName;
    private String gender;
    private String says;
    private String college;
    private String birthday;
    private BmobFile photoImage;


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSays() {
        return says;
    }

    public void setSays(String says) {
        this.says = says;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public BmobFile getPhotoImage() {
        return photoImage;
    }

    public void setPhotoImage(BmobFile photoImage) {
        this.photoImage = photoImage;
    }
}
