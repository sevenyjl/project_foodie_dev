package com.seven.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author seven
 * @Date 9/3/2020 22:00
 * @Version 1.0
 **/
public class DateUtils {
    public static String data2String(Date date, String patt){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patt);
        return simpleDateFormat.format(date);
    }

    public static String data2String(java.sql.Date date,String patt){
        return data2String(new Date(date.getTime()),patt);
    }

    public static Date string2Date(String time,String patt){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patt);
        try {
            return simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static java.sql.Date string2DateSql(String time,String patt){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patt);
        try {
            return new java.sql.Date(simpleDateFormat.parse(time).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
