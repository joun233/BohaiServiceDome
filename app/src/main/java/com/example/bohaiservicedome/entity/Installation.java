package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 11
 */
public class Installation extends BmobInstallation {
    private User user;
    private BmobGeoPoint location;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BmobGeoPoint getLocation() {
        return location;
    }

    public void setLocation(BmobGeoPoint location) {
        this.location = location;
    }
}
