package com.dongyuwuye.compontent_sdk.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chao on 2016/11/4.
 * 控制activity的销毁
 */

public class ActivityControler {
    private static ActivityControler Instance = null;

    private ActivityControler() {

    }

    public static ActivityControler getInstance() {
        if (Instance == null) {
            Instance = new ActivityControler();
        }
        return Instance;
    }

    private static List<Activity> activities = new ArrayList<Activity>();

    public void AddAcitivity(Activity activity) {
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //移除处主界面外的所有activity
    public void finishOther() {
        for (int i = 0; i < activities.size(); i++) {
            if (i > 0) {
                if (!activities.get(i).isFinishing()) {
                    activities.get(i).finish();
                }
            }
        }
    }

    //移除所有activity
    public void finished() {
        for (Activity acitivty : activities) {
            acitivty.finish();
        }
    }
}
