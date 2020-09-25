package com.example.bohaiservicedome.entity;

import java.util.Calendar;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/6 20
 */
public class CalenderEvent {
    private String message;
    private Calendar mCalendar;

    public CalenderEvent(String message, Calendar calendar) {
        this.message = message;
        mCalendar = calendar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }
}
