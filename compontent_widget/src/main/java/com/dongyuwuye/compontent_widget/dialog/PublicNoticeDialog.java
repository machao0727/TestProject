package com.dongyuwuye.compontent_widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.dongyuwuye.compontent_sdk.utils.DensityUtils;
import com.dongyuwuye.compontent_sdk.utils.ScreenUtils;
import com.dongyuwuye.compontent_widget.R;
import com.dongyuwuye.compontent_widget.R2;
import com.lihang.ShadowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by：mc on 2019/11/14 20:20
 * email:
 */
public class PublicNoticeDialog extends Dialog {

    @BindView(R2.id.mTvTitle)
    TextView mTvTitle;

    @BindView(R2.id.mIvCancel)
    ImageView mIvCancel;

    @BindView(R2.id.mTvContent)
    TextView mTvContent;

    @BindView(R2.id.mDoubleLayout)
    LinearLayout mDoubleLayout;

    @BindView(R2.id.mBtnLeft)
    TextView mBtnLeft;

    @BindView(R2.id.mBtnRight)
    TextView mBtnRight;

    @BindView(R2.id.mBtnSingle)
    TextView mBtnSingle;

    @BindView(R2.id.mBtnSingleLayout)
    ShadowLayout mBtnSingleLayout;


    @BindView(R2.id.mRightShadow)
    ShadowLayout mRightShadow;

    private Context context;

    public PublicNoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
        this.context = context;
    }


    private void initView(Context context) {
        getWindow().setContentView(R.layout.dialog_public_notice);
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtils.getScreenW() - DensityUtils.dpTopx(100, context)); //设置宽度
        getWindow().setAttributes(lp);
        getWindow().setDimAmount(0.1f);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public PublicNoticeDialog setContentLeft() {
        mTvContent.setGravity(Gravity.LEFT);
        return this;
    }

    public PublicNoticeDialog setRightShadowColor(int color) {
        mRightShadow.setmShadowColor(color);
        return this;
    }

    public PublicNoticeDialog setRightColor(int drawable) {
        mBtnRight.setBackgroundResource(drawable);
        return this;
    }

    public PublicNoticeDialog setSingleOnClick(String text, View.OnClickListener listener) {
        mBtnSingle.setText(text);
        mDoubleLayout.setVisibility(View.GONE);
        mBtnSingle.setOnClickListener(listener);
        return this;
    }

    public PublicNoticeDialog setDoubleOnClick(String left, View.OnClickListener leftListener, String right, View.OnClickListener rightListener) {
        mBtnSingleLayout.setVisibility(View.GONE);
        mBtnLeft.setText(left);
        mBtnRight.setText(right);
        mBtnLeft.setOnClickListener(leftListener);
        mBtnRight.setOnClickListener(rightListener);
        return this;
    }

    public PublicNoticeDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public PublicNoticeDialog setContent(String text) {
        mTvContent.setText(text);
        return this;
    }

    public PublicNoticeDialog setContent(SpannableString text) {
        mTvContent.setText(text);
        return this;
    }
}
