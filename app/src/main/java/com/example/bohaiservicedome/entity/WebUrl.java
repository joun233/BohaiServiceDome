package com.example.bohaiservicedome.entity;

import java.io.Serializable;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/2 09
 */
public class WebUrl implements Serializable {
    String title;
    String url;

    public WebUrl(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
