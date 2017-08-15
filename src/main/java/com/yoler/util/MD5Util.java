package com.yoler.util;

import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author zhangyu
 */
public class MD5Util {
    public static final String CHARSET_GBK = "GBK";
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 32位MD5(默认编码UTF-8)
     *
     * @param s
     * @return
     */
    public final static String getMD532(String s) {
        return getMD532(s, CHARSET_UTF8);
    }

    /**
     * 32位MD5(自定义编码)
     *
     * @param s
     * @param charset
     * @return
     */
    public final static String getMD532(String s, String charset) {
        try {
            byte[] btInput = s.getBytes(charset);
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            return byteToHex(mdInst.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param bytes
     * @return
     */
    public final static String getMD532(byte[] bytes) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(bytes);
            return byteToHex(mdInst.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 16位MD5(前十六位,默认编码GBK)
     *
     * @param str
     * @return
     */
    public static String getMD516(String str) {
        String result = getMD532(str);
        if (result == null) {
            return null;
        }
        return result.substring(0, 16);
    }

    /**
     * 16位MD5(前十六位,自定义编码)
     *
     * @param str
     * @param charset
     * @return
     */
    public static String getMD516(String str, String charset) {
        String result = getMD532(str, charset);
        if (result == null) {
            return null;
        }
        return result.substring(0, 16);
    }

    /**
     * 把密文转换成十六进制的字符串形式
     *
     * @param b
     * @return
     */
    private static String byteToHex(byte[] b) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        int len = b.length;
        char[] chars = new char[len * 2];
        int k = 0;
        for (int i = 0; i < len; i++) {
            byte byte0 = b[i];
            chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
            chars[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(chars);
    }

}
