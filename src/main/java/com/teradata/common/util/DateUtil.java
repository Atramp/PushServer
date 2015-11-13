package com.teradata.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Liuyueteng
 * @date 2014/6/27
 */
public class DateUtil {

    /**
     * 在当前日期的基础上增减天数
     */
    public static String addDays(int day_offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day_offset);
        return sdf.format(c.getTime());
    }

    /**
     * 在指定日期的基础上增减天数
     */
    public static String addDays(String date, int day_offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        Date d;
        try {
            d = sdf.parse(date);
            c.setTime(d);
            c.add(Calendar.DAY_OF_MONTH, day_offset);
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按月偏移
     */
    public static String addMonths(String date, int month_offset) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        Date d;
        try {
            d = sdf.parse(date);
            c.setTime(d);
            c.add(Calendar.MONTH, month_offset);
            return sdf.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取今天
     */
    public static String today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        return sdf.format(c.getTime());
    }

    /**
     * 获取昨天
     */
    public static String yesterday() {
        return addDays(-1);
    }

    /**
     * 获取明天
     */
    public static String tomorrow() {
        return addDays(1);
    }

    public static String getStandardDate() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    public static String getStandardTimestamp() {
        return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
    }

    public static String getStandardTimestamp(int size) {
        String str = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        int startIndex = str.length() - size;
        String stamp = startIndex < 0 ? str : str.substring(startIndex);
        return stamp.charAt(0) == '0' ? stamp.replace('0','1'): stamp;
    }
}
