package com.example.bohaiservicedome.entity;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/1 20
 */
public class HomeIconInfo {
    String iconName;
    int iconID;

    public HomeIconInfo(String iconName, int iconID) {
        this.iconName = iconName;
        this.iconID = iconID;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
