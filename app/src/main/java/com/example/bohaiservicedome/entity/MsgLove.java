package com.example.bohaiservicedome.entity;

import cn.bmob.v3.BmobObject;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/5 12
 */
public class MsgLove extends BmobObject {
    private String username;
    private String to;
    private String from;
    private String content;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
