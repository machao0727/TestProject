package com.dongyuwuye.compontent_sdk.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.dongyuwuye.compontent_sdk.BuildConfig;


/**
 * create by：mc on 2018/5/17 09:59
 * <p>
 * email：
 */
public class ActivityUtils {
    private static final String SIGN = "activity_start_sign";
    private static final String SIGN_PWD = "activity_start_sign_pwd";

    public static void start(Context context, Intent intent) {
        context.startActivity(signature(intent));
    }

    public static Intent signature(@NonNull Intent intent) {
        String keyword = "SIGN@" + System.currentTimeMillis();
        intent.putExtra(SIGN, keyword);
        intent.putExtra(SIGN_PWD, MD5Utils.encode(keyword));
        return intent;
    }

    public static boolean checkSignature(@NonNull Intent intent) {
        String action = intent.getComponent().getClassName();
        String sign = intent.getStringExtra(SIGN);
        String signPwd = intent.getStringExtra(SIGN_PWD);
        return MAIN.equals(action) || intent.hasExtra("tag.tpush.NOTIFIC")
                || (!TextUtils.isEmpty(sign)
                && !TextUtils.isEmpty(signPwd)
                && signPwd.equals(MD5Utils.encode(sign)));
    }

    private static String MAIN;

    public static void initMain(Application context, String id) {
        MAIN = context.getPackageManager()
                .getLaunchIntentForPackage(id)
                .getComponent()
                .getClassName();
    }
}
