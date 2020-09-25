package com.example.bohaiservicedome.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/3 17
 */
public class DateUtils {
    /**
     * 将yyyy-MM-dd HH:mm:ss 转换为Date
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Calendar strToCalendar(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        calendar.setTime(formatter.parse(strDate, pos));
        return calendar;
    }

    /**
     * 判断是不是今天
     *
     * @param calendar
     * @return
     */
    public static boolean isTody(Calendar calendar) {
        Calendar nowCalender = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DATE);
        int nYear = nowCalender.get(Calendar.YEAR);
        int nMonth = nowCalender.get(Calendar.MONTH);
        int nDay = nowCalender.get(Calendar.DAY_OF_MONTH);
        if (Year == nYear && Month == nMonth && Day == nDay) {
            return true;
        }
        return false;
    }

    public static String getDayOfWeek(){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[w];
    }

}
