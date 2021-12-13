package com.dongyuwuye.compontent_sdk.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

public class SpannableUtils {

    public static SpannableString setSpanColor(SpannableString sb, Context context, int Color, int start, int end) {
        sb.setSpan(new ForegroundColorSpan(context.getResources().getColor(Color)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static SpannableString setSpanSize(SpannableString sb, int size, int start, int end) {
        sb.setSpan(new AbsoluteSizeSpan(size, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}
