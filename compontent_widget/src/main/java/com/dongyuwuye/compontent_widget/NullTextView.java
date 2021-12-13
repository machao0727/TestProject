package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.dongyuwuye.compontent_sdk.utils.StringUtils;

/**
 * create by：mc on 2019/11/14 14:49
 * email:
 */
public class NullTextView extends AppCompatTextView {

    public void setMSingleLine(boolean singleLine) {
        isSingleLine = singleLine;
        setSingleLine(singleLine);
        if (StringUtils.isNotNullOrEmpty(text.toString())) {
            setContent(text.toString());
        }
    }

    private boolean isSingleLine=true;

    private CharSequence text;

    public NullTextView(Context context) {
        super(context);
    }

    public NullTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NullTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTextView(CharSequence oldTest) {
        if (oldTest == null || oldTest.length() == 0) {
            text = "--";
            SpannableString sp = new SpannableString(text);
            sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.widget_gray6)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(sp);
        } else {
            text=oldTest;
            setText(oldTest);
        }
    }

    public void setTextView(SpannableString oldTest) {
        if (oldTest == null || oldTest.length() == 0) {
            text = "--";
            SpannableString sp = new SpannableString(text);
            sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.widget_gray6)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(sp);
        } else {
            text=oldTest;
            setText(oldTest);
        }
    }

    private void setContent(String text) {
        if (text.length() > 11 && isSingleLine) {//不止一个号码，并且是单行显示
            setText(String.format("%s...", text.substring(0, 11)));
        } else {
            setText(text);
        }
    }

    public void setTextViewToPhone(CharSequence oldTest) {
        if (oldTest == null || oldTest.length() == 0) {
            text = "--";
            SpannableString sp = new SpannableString(text);
            sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.widget_gray6)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(sp);
        } else {
            text = ToSBC(oldTest.toString());
            setContent(text.toString());
        }
    }

    private String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
