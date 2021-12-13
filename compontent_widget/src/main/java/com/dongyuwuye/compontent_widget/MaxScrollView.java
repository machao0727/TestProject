package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.dongyuwuye.compontent_sdk.utils.DensityUtils;

/**
 * create by：mc on 2019/12/9 15:15
 * email:
 */
public class MaxScrollView extends NestedScrollView {

    private int mMaxHeight;

    public MaxScrollView(@NonNull Context context) {
        super(context);
        mMaxHeight= DensityUtils.dpTopx(250,context);
    }

    public MaxScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMaxHeight= DensityUtils.dpTopx(250,context);
    }

    public MaxScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMaxHeight= DensityUtils.dpTopx(250,context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= mMaxHeight ? heightSize
                    : (int) mMaxHeight;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }
}
