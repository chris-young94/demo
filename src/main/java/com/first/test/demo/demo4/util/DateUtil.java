package com.first.test.demo.demo4.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
    private static final long DIFF_SECOND = 1000L;
    private static final long DIFF_MINUTE = 60000L;
    private static final long DIFF_HOUR = 3600000L;
    private static final long DIFF_DAY = 86400000L;
    private static final long DIFF_DAY_30 = 2592000000L;
    private static final long DIFF_DAY_31 = 2678400000L;
    private static final long DIFF_DAY_365 = 31536000000L;
    /**
     * 线程安全的日期格式对象
     */
    protected static final ThreadLocal<DateFormat> YYMMDDHHMMSSSSS = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            // 完整日期
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return df;
        }

    };

    /**
     * 获取当前时间精确到毫秒
     */
    protected static String getPresentTime() {
        return YYMMDDHHMMSSSSS.get().format(new Date());
    }

    public static String getPresentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

}