package com.yoler.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zhangyu on 2017/6/21.
 */
public class AESUtil {

    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    static final String KEY_ALGORITHM = "AES";

    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @param iv
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, String sKey, String iv) throws Exception {
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));// 使用加密模式初始化
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));// 使用解密模式初始化
        // 密钥
        return bytesToHexString(encrypted).toLowerCase();

    }

    /**
     *
     */
    public static String DecryptString(String sSrc, String sKey, String iv) throws Exception {
        byte[] raw = sKey.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher;
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv.getBytes()));// 使用解密模式初始化
        // 密钥
        byte[] decrypt = cipher.doFinal(hexStr2Bytes(sSrc));
        return new String(decrypt);

    }

    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        String encrypt = Encrypt("xu09t0hls39i126usj9857e3xtqpcxkz", "1122334455667788", "8877665544332211");
        System.out.println(encrypt);
        String de = DecryptString("1d44bb740d4bbd4bd85c1ccb751f2780d1e6f9e586c30c629ad062e4c76f15d37331f171a3e05d58da32c4726afd0a64",
                "1122334455667788", "8877665544332211");
        System.out.println(de);
    }


}
