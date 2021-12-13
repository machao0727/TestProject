package com.dongyuwuye.compontent_widget.RoundRelativeLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.dongyuwuye.compontent_widget.R;


/**
 * 自定义控件：圆角RelativeLayout
 */
public class RoundRelativeLayout extends RelativeLayout {
    private RoundViewDelegate mRoundViewDelegate;
    private boolean wh;

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }


    public RoundRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }


    public RoundRelativeLayout(Context context) {
        super(context);
        initView(context, null, 0);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRelativeLayout, defStyle, 0);
        int radius = a.getDimensionPixelOffset(R.styleable.RoundRelativeLayout_roundRadius, 10);
        wh = a.getBoolean(R.styleable.RoundRelativeLayout_WH, true);
        if (mRoundViewDelegate == null) {
            mRoundViewDelegate = new RoundViewDelegate(this, getContext());
        }
        mRoundViewDelegate.setRectAdius(radius);
        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        mRoundViewDelegate.roundRectSet(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        mRoundViewDelegate.canvasSetLayer(canvas);
        super.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 设置View宽高的测量值
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        // 只有setMeasuredDimension调用之后，才能使用getMeasuredWidth()和getMeasuredHeight()来获取视图测量出的宽高，以此之前调用这两个方法得到的值都会是0
        int childWidthSize = getMeasuredWidth();

        // 高度和宽度一样
        if (wh) {
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                    childWidthSize, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
