package com.example.bohaiservicedome.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/3 13
 */
public class FormatUtils {
    public static final String template_Date = "yyyy-MM-dd";
    public static final String template_DbDateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String template_Time = "HH:mm:ss";
    public static Date parseDate(String dateString, String template) {
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateTimeString(Date date, String template) {
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.getDefault());
        return format.format(date);
    }

}
