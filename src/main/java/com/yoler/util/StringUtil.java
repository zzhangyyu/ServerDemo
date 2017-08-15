package com.yoler.util;

/**
 * 字符串工具类
 * Created by zhangyu on 2017/6/19.
 */
public class StringUtil {

    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 为null添加空字符串
     *
     * @param str
     * @return
     */
    public static String addEmpty(String str) {
        return str == null ? "" : str;
    }
}
