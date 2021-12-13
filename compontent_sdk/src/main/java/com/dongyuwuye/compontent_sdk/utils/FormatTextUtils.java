package com.dongyuwuye.compontent_sdk.utils;

import android.text.SpannableString;
import android.widget.TextView;

import com.dongyuwuye.compontent_sdk.R;

/**
 * create by：mc on 2020/4/9 9:42
 * email:
 */
public class FormatTextUtils {

    public static void setTextView(String text, int start, int end, TextView view) {
        SpannableString sp = new SpannableString(text);
        SpannableUtils.setSpanColor(sp, ApplicationUtils.application, R.color.sdk_black, start, end);
        view.setText(sp);
    }
}
