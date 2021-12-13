package com.dongyuwuye.compontent_base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;


import com.iflytek.cloud.msc.util.NetworkUtil;

public class BaseNetStateChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            if (NetworkUtil.isNetworkAvailable(context)) {
                doSomething();
            } else {
                hasNet();
            }
        }
    }

    protected void doSomething() {

    }

    protected void hasNet() {

    }
}
