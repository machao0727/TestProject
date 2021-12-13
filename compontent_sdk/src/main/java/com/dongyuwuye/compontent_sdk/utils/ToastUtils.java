package com.dongyuwuye.compontent_sdk.utils;

import android.widget.Toast;

/**
 * create by：mc on 2019/11/21 14:19
 * email:
 */
public class ToastUtils {
    public static void toast(String text) {
        Toast.makeText(ApplicationUtils.application, text, Toast.LENGTH_SHORT).show();
    }
}
