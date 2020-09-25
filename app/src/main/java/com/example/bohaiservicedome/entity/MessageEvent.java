package com.example.bohaiservicedome.entity;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/3 09
 */
public class MessageEvent {
    public MessageEvent(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
