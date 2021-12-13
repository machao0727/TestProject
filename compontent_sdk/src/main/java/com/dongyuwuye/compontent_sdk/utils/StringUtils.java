package com.dongyuwuye.compontent_sdk.utils;

/**
 * create by：mc on 2019/11/25 9:25
 * email:
 */
public class StringUtils {

    /**
     * @return true if the string is not null, empty string "", or the length is greater than 0
     */
    public static boolean isNotNullOrEmpty(String inString) {
        return inString != null && !inString.equals("") && inString.length() > 0;
    }

    /**
     * @return true if the string is null, empty string "", or the length is less than equal to 0
     */
    public static boolean isNullOrEmpty(String inString) {
        return inString == null || "".equals(inString) || "null".equals(inString) || inString.length() == 0;
    }


    public static String isNullOrEmptyPlace(String str, String... arg) {
        String place = "--";
        if (arg.length > 0) {
            place = arg[0];
        }
        return isNullOrEmpty(str) ? place : str;
    }
}
