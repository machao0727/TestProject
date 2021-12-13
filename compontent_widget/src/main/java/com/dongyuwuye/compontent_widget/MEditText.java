package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * create by：mc on 2020/4/1 15:42
 * email:
 */
public class MEditText extends AppCompatEditText {
    public MEditText(Context context) {
        super(context);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
