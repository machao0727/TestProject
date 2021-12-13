package com.dongyuwuye.compontent_sdk.utils;

import java.security.MessageDigest;

/**
 * Created by chao on 2016/11/28.
 * MD5加密
 */

public class MD5Utils {
    //字符串进行MD5加密
    public static String encode(String password) {//MD5加密
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                int i = b & 0xff;
                String hexString = Integer.toHexString(i);
                if (hexString.length() == 1) {
                    hexString = "0" + hexString;
                }
                sb.append(hexString);
            }
            String md5 = sb.toString();
            return md5;
        } catch (Exception e) {
            //没有此算法的异常
            e.printStackTrace();
        }
        return "";
    }

}

