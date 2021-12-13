package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.dongyuwuye.compontent_sdk.utils.DensityUtils;
import com.dongyuwuye.compontent_sdk.utils.StringUtils;
import com.lihang.ShadowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * create by：mc on 2019/12/6 17:22
 * email:
 */
public class ChooseView<T> extends LinearLayout {

    private Context context;

    public static final int CONTENT_TYPE_TEXT = 0x00000000;
    public static final int CONTENT_TYPE_EDITTEXT = 0x00000001;
    public static final int INPUT_TYPE_NUMBER = 0x00000002;
    public static final int INPUT_TYPE_NUMBER_DECIMAL = 0x00000003;

    @BindView(R2.id.mTvTitle)
    TextView mTvTitle;

    @BindView(R2.id.mSdLayout)
    ShadowLayout mSdLayout;

    @BindView(R2.id.mTvContent)
    TextView mTvContent;

    @BindView(R2.id.mIvIcon)
    TextView mIvIcon;

    @BindView(R2.id.mTvRight)
    TextView mTvRight;

    @BindView(R2.id.mTvNotice)
    TextView mTvNotice;

    @BindView(R2.id.mRightTextLayout)
    FrameLayout mRightTextLayout;

    @BindView(R2.id.mEtContent)
    EditText mEtContent;

    @BindView(R2.id.mIconLayout)
    FrameLayout mIconLayout;

    @BindView(R2.id.mLeftIconLayout)
    FrameLayout mLeftIconLayout;

    @BindView(R2.id.mIvLeftIcon)
    TextView mIvLeftIcon;
    private SpnnerPop<T> pop;
    private String id;

    public ChooseView(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public ChooseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.widget_choose_view_layout, this, true);
        ButterKnife.bind(this, view);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChooseView, defStyleAttr, 0);
        int icon = a.getResourceId(R.styleable.ChooseView_icon, -1);
        if (icon > 0) {
            Drawable drawable1 = context.getResources().getDrawable(icon);
            drawable1.setBounds(0, 0, DensityUtils.dpTopx(10, context), DensityUtils.dpTopx(10, context));
            mIvIcon.setCompoundDrawables(drawable1, null, null, null);
        }
        String iconText = a.getString(R.styleable.ChooseView_iconText);
        if (StringUtils.isNotNullOrEmpty(iconText)) {
            mIvIcon.setText(iconText);
        }
        if (icon < 0 && StringUtils.isNullOrEmpty(iconText)) {
            mIconLayout.setVisibility(GONE);
        }
        int rightIcon = a.getResourceId(R.styleable.ChooseView_rightIcon, -1);
        if (rightIcon > 0) {
            Drawable drawable = context.getResources().getDrawable(rightIcon);
            drawable.setBounds(0, 0, DensityUtils.dpTopx(5, context), DensityUtils.dpTopx(5, context));
            mTvRight.setCompoundDrawables(drawable, null, null, null);
        }
        String rightText = a.getString(R.styleable.ChooseView_rightText);
        if (StringUtils.isNotNullOrEmpty(rightText)) {
            mTvRight.setText(rightText);
        }
        if (rightIcon <= 0 && StringUtils.isNullOrEmpty(rightText)) {
            mRightTextLayout.setVisibility(GONE);
        }
        int leftIcon = a.getResourceId(R.styleable.ChooseView_leftIcon, -1);
        if (leftIcon > 0) {
            Drawable drawable = context.getResources().getDrawable(leftIcon);
            drawable.setBounds(0, 0, DensityUtils.dpTopx(10, context), DensityUtils.dpTopx(10, context));
            mIvLeftIcon.setCompoundDrawables(drawable, null, null, null);
        } else {
            mLeftIconLayout.setVisibility(GONE);
        }
        String title = a.getString(R.styleable.ChooseView_title);
        int titleSize = a.getDimensionPixelOffset(R.styleable.ChooseView_titleSize, -1);
        if (StringUtils.isNotNullOrEmpty(title)) {
            mTvTitle.setText(title);
        } else {
            mTvTitle.setVisibility(GONE);
        }

        if (titleSize > 0) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        }
        int contentType = a.getInt(R.styleable.ChooseView_contentType, CONTENT_TYPE_TEXT);
        int inputType = a.getInt(R.styleable.ChooseView_inputType, INPUT_TYPE_NUMBER);
        boolean isNeed = a.getBoolean(R.styleable.ChooseView_isNeed, true);//默认必填
        setNeed(isNeed);
        if (contentType == CONTENT_TYPE_TEXT) {
            mTvContent.setVisibility(VISIBLE);
            mEtContent.setVisibility(GONE);
        } else {
            mTvContent.setVisibility(GONE);
            mEtContent.setVisibility(VISIBLE);
        }
        setInputType(inputType);

        String textHint = a.getString(R.styleable.ChooseView_textHint);
        if (!StringUtils.isNullOrEmpty(textHint)) {
            mTvContent.setHint(textHint);
            mEtContent.setHint(textHint);
        }

        int textHintColor = a.getInt(R.styleable.ChooseView_textHintColor, -1);
        if (textHintColor > 0) {
            mTvContent.setHintTextColor(textHintColor);
            mEtContent.setHintTextColor(textHintColor);
        }

        int contentTextSize = a.getDimensionPixelOffset(R.styleable.ChooseView_contentTextSize, -1);
        if (contentTextSize > 0) {
            mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
            mEtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        }

        a.recycle();
        initEvent();
    }

    public void setNeed(boolean isNeed) {
        if (isNeed) {//必填
            mTvNotice.setVisibility(GONE);
        } else {
            mTvNotice.setVisibility(VISIBLE);
        }
    }

    public void setCenter() {
        mEtContent.setGravity(Gravity.CENTER);
        mTvContent.setGravity(Gravity.CENTER);
    }

    private void initEvent() {
        mSdLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items != null) {
                    showPop();
                }
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    public void setContentType(int contentType) {
        if (contentType == CONTENT_TYPE_TEXT) {
            mTvContent.setVisibility(VISIBLE);
            mEtContent.setVisibility(GONE);
        } else {
            mTvContent.setVisibility(GONE);
            mEtContent.setVisibility(VISIBLE);
        }
    }

    public void setHint(SpannableString sb) {
        mEtContent.setHint(sb);
        mTvContent.setHint(sb);
    }

    public void setHint(String text) {
        mEtContent.setHint(text);
        mTvContent.setHint(text);
    }

    public void setText(String text) {
        if (mEtContent.getVisibility() == VISIBLE) {
            mEtContent.setText(text);
        } else {
            mTvContent.setText(text);
        }
    }

    private int showX = 0;

    private List<T> items;

    public void setItems(List<T> items) {
        this.items = items;
        if (pop != null) {
            pop.setItems(items);
        }
    }

    public void reset() {
        pop.reset();
    }

    /**
     * 展示下拉列表
     */
    private void showPop() {
        if (items.size() > 0) {
            if (pop == null) {
                int width = mSdLayout.getWidth();
                showX = (int) mSdLayout.getX();
                pop = new SpnnerPop<T>(context, new SpnnerPop.OnChooseListener<T>() {
                    @Override
                    public void onChoose(T item) {
                        if (listener != null) {
                            listener.onChoose(item);
                        }
                        pop.dismiss();
                    }
                }, items, width, id);
                pop.setAnimationStyle(0);
            }
            pop.setImage(getImageBitmap());
            pop.showAsDropDown(mSdLayout, 0, -DensityUtils.dpTopx(57, context));
        } else {
            Toast.makeText(context, "无多余选项", Toast.LENGTH_SHORT).show();
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    private Bitmap getImageBitmap() {
        mSdLayout.setDrawingCacheEnabled(false);
        mSdLayout.buildDrawingCache(false);
        mSdLayout.setDrawingCacheEnabled(true);
        mSdLayout.buildDrawingCache(true);
        return mSdLayout.getDrawingCache();
    }

    /**
     * 输入模式
     *
     * @param contentType
     */
    public void setIconType(int contentType) {
        if (contentType == CONTENT_TYPE_TEXT) {
            mIconLayout.setVisibility(GONE);
            mTvRight.setText("元");
            mTvRight.setCompoundDrawables(null, null, null, null);
        } else {
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_must);
            drawable.setBounds(0, 0, DensityUtils.dpTopx(5, context), DensityUtils.dpTopx(5, context));
            mTvRight.setCompoundDrawables(drawable, null, null, null);
            mTvRight.setText("");
            mIconLayout.setVisibility(VISIBLE);
            mIvIcon.setCompoundDrawables(null, null, null, null);
            mIvIcon.setText("元");
        }
    }

    /**
     * 获取输入框数据
     *
     * @return
     */
    public String getText() {
        if (mEtContent.getVisibility() == VISIBLE) {
            return mEtContent.getText().toString();
        } else {
            return mTvContent.getText().toString();
        }
    }

    public void setInputType(int inputType) {
        //金额模式
        if (inputType == INPUT_TYPE_NUMBER_DECIMAL) {
            setInputTypeNumberDecimal();
        } else {//整数模式
            mEtContent.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    private void setInputTypeNumberDecimal() {
        mEtContent.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
//                        editText.setText("0.00");
                    return;
                } else {
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                            s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                            mEtContent.setText(s);
                            mEtContent.setSelection(s.length());
                        }
                    }

                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        mEtContent.setText(s);
                        mEtContent.setSelection(2);
                    }

                    if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            mEtContent.setText(s.subSequence(0, 1));
                            mEtContent.setSelection(1);
                            return;
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private onChooseListener<T> listener;

    public void setOnChooseListener(onChooseListener<T> listener) {
        this.listener = listener;
    }

    public interface onChooseListener<T> {
        void onChoose(T item);
    }

    private OnClickListener clickListener;

    public void setOnClickListener(OnClickListener listener) {
        clickListener = listener;
    }
}
