package com.dongyuwuye.compontent_sdk.utils;

import android.util.Log;


import com.dongyuwuye.compontent_sdk.BuildConfig;

/**
 * create by：mc on 2019/10/30 15:15
 * email:
 */
public class LogUtils {
    public static void e(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg);
    }

    public static void e(String TAG, String msg, Exception e) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, msg, e);
    }

    public static void d(String TAG, String msg) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, msg);
    }

}
