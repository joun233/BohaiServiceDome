package com.example.bohaiservicedome.entity;

import android.net.Uri;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/13 13
 */
public class UriEvent {
    private String message;
    private Uri uri;

    public UriEvent(String message, Uri uri) {
        this.message = message;
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
