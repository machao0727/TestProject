package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.dongyuwuye.compontent_sdk.utils.DensityUtils;

/**
 * create by：mc on 2019/11/14 20:28
 * email:
 */
public class MaxHeightScrollView extends ScrollView {
    private Context context;

    public MaxHeightScrollView(Context context) {
        super(context);
        this.context=context;
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DensityUtils.dpTopx(250,context), MeasureSpec.AT_MOST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //重新计算控件高、宽
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
