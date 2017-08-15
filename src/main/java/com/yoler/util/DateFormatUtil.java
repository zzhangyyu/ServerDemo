package com.yoler.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangyu on 2017/6/19.
 */
public class DateFormatUtil {

    private final static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 日期格式.
     */
    public enum Format {

        DATETIME("yyyy-MM-dd HH:mm:ss"),

        DATETIME_TIME_ZONE("yyyy-MM-dd HH:mm:ssZZ"),

        DATE("yyyy-MM-dd"),

        TIME("HH:mm:ss"),

        TIME_NO_T_TIME_ZONE("HH:mm:ssZZ"),

        YEAR("yyyy"),

        YEAR_TWO("yyyy"),

        MONTH("MM"),

        MONTH_ONE("M"),

        YEAR_MONTH("yyyy-MM"),

        HOURE_MINUTE("HH:mm"),;

        private String value;

        private Format(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    /**
     * 格式化日期为"yyyy-MM-dd HH:mm:ss"格式
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, Format.DATETIME.toString());
    }

    /**
     * 格式化日期为"yyyy-MM-dd"格式
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return formatDate(date, Format.DATE.toString());
    }

    /**
     * 格式化日期为"HH:mm:ss"格式.
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return formatDate(date, Format.TIME.toString());
    }

    /**
     * 按指定的格式格式化日期, 默认格式: "yyyy-MM-dd"
     *
     * @param date
     * @param pattern
     * @return string
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        String formatDate = null;
        if (pattern == null) {
            pattern = Format.DATE.toString();
        }
        DateFormat df = new SimpleDateFormat(pattern);
        formatDate = df.format(date);
        return formatDate;
    }

    /**
     * 字符串转化为日期, 默认格式: "yyyy-MM-dd".
     *
     * @param str
     * @param pattern
     * @return date
     * @throws ParseException
     */
    public static Date parseDateTime(String str, String pattern) throws Exception {
        Date result = null;
        if (StringUtil.isEmpty(str)) {
            throw new Exception("日期格式不正确");
        }
        if (StringUtil.isEmpty(pattern)) {
            pattern = Format.DATE.toString();
        }
        try {
            DateFormat df = new SimpleDateFormat(pattern);
            df.setLenient(false);
            result = df.parse(str);
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * 字符串转化为日期, 默认格式: "yyyy-MM-dd"
     *
     * @param str
     * @return
     */
    public static Date parseDateTime(String str) {
        Date result = null;
        try {
            result = parseDateTime(str, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前日期是星期几
     *
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return weekDays[week];
    }

    /**
     * 获取当前日期是星期几
     *
     * @param dateStr
     * @return
     */
    public static String getWeekOfDate(String dateStr) {
        try {
            Date date = parseDateTime(dateStr, "");
            return getWeekOfDate(date);
        } catch (Exception e) {

        }
        return null;
    }
}
