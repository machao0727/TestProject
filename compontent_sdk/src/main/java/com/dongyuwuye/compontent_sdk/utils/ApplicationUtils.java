package com.dongyuwuye.compontent_sdk.utils;

import android.app.Application;


/**
 * create by：mc on 2018/5/21 16:03
 * <p>
 * email:
 */
public class ApplicationUtils {

    public static Application application;

    public static void init(Application context) {
        application = context;
    }

}
