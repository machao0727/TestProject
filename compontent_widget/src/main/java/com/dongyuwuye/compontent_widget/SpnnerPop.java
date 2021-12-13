package com.dongyuwuye.compontent_widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dongyuwuye.compontent_sdk.utils.StringUtils;
import com.dongyuwuye.compontent_widget.model.CarsInfo;
import com.dongyuwuye.compontent_widget.model.FeesItemResp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * create by：mc on 2019/12/9 13:56
 * email:
 */
public class SpnnerPop<T> extends PopupWindow {

    private Context mContext;

    private OnChooseListener<T> listener;

    private List<T> Items;

    @BindView(R2.id.mLLContent)
    LinearLayout mLLContent;

    @BindView(R2.id.mParentLayout)
    FrameLayout mParentLayout;

    @BindView(R2.id.mIvImage)
    ImageView mIvImage;

    private TextView lastSelected;
    private TextView newSelected;
    private int width;
    private String id;

    public SpnnerPop(Context context, OnChooseListener<T> listener, List<T> items, int width, String id) {
        super(context);
        mContext = context;
        this.listener = listener;
        this.width = width;
        this.id = id;
        Items = items;
        initView();
        setContent();
    }

    public void setItems(List<T> items) {
        Items = items;
        mLLContent.removeAllViews();
        setContent();
    }

    public void setImage(Bitmap bitmap) {
        mIvImage.setImageBitmap(bitmap);
    }

    private void setContent() {
        for (int i = 0; i < Items.size(); i++) {
            View item = View.inflate(mContext, R.layout.item_spnner_layout, null);
            final TextView mTvSpnnerItem = item.findViewById(R.id.mTvSpnnerItem);
            if (Items.get(i) instanceof String) {
                mTvSpnnerItem.setText((String) Items.get(i));
                if (StringUtils.isNotNullOrEmpty(id) && Items.get(i).equals(id)) {
                    mTvSpnnerItem.setSelected(true);
                    lastSelected = mTvSpnnerItem;
                    newSelected = mTvSpnnerItem;
                }
            }
            if (Items.get(i) instanceof FeesItemResp) {
                mTvSpnnerItem.setText(((FeesItemResp) Items.get(i)).getCostName());
                if (StringUtils.isNotNullOrEmpty(id) && (((FeesItemResp) Items.get(i)).getCostID() + ((FeesItemResp) Items.get(i)).getStanID()).equals(id)) {
                    mTvSpnnerItem.setSelected(true);
                    lastSelected = mTvSpnnerItem;
                    newSelected = mTvSpnnerItem;
                }
            }
            if (Items.get(i) instanceof CarsInfo) {
                mTvSpnnerItem.setText(((CarsInfo) Items.get(i)).getParkName());
                if (StringUtils.isNotNullOrEmpty(id) && ((CarsInfo) Items.get(i)).getHandID().equals(id)) {
                    mTvSpnnerItem.setSelected(true);
                    lastSelected = mTvSpnnerItem;
                    newSelected = mTvSpnnerItem;
                }
            }
            mLLContent.addView(item);
            final int finalI = i;
            mTvSpnnerItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Items.get(finalI) instanceof CarsInfo) {
                        if (StringUtils.isNotNullOrEmpty(((CarsInfo) Items.get(finalI)).getStanID()) && ((CarsInfo) Items.get(finalI)).getStanID().equals("0")) {
                            Toast.makeText(mContext, "该车位无费用标准，请移步ERP进行绑定", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (!v.isSelected()) {
                        v.setSelected(true);
                        if (lastSelected != null) {
                            lastSelected = newSelected;
                            newSelected.setSelected(false);
                        } else {
                            lastSelected = mTvSpnnerItem;
                        }
                        newSelected = mTvSpnnerItem;
                        listener.onChoose(Items.get(finalI));
                    } else {
                        listener.onChoose(Items.get(finalI));
                    }
                }
            });
        }
    }

    public void reset() {
        if (lastSelected != null) lastSelected.setSelected(true);
        if (newSelected != null) newSelected.setSelected(false);
    }

    /**
     * 初始化
     */
    private void initView() {
        View view = View.inflate(mContext, R.layout.pop_choose_spnner_layout, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.setBackgroundDrawable(new ColorDrawable());
        this.setOutsideTouchable(true);
        this.setFocusable(true);
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public interface OnChooseListener<T> {
        void onChoose(T item);
    }
}
