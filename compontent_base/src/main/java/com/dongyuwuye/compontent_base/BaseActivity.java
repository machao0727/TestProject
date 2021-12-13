package com.dongyuwuye.compontent_base;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;


import com.dongyuwuye.compontent_base.annotation.ActivityFeature;
import com.dongyuwuye.compontent_base.receiver.BaseNetStateChangeReceiver;
import com.dongyuwuye.compontent_sdk.utils.ActivityUtils;
import com.dongyuwuye.compontent_widget.progressdialog.MProgressDialog;
import com.dongyuwuye.compontent_widget.state.StateLayout;
import com.jaeger.library.StatusBarUtil;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mc on 2018/3/28.
 * 基类activity
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public Context context;
    public Activity mContext;


    protected Toolbar mToolbar;

    protected TextView mTitleView;

    protected TextView rightTitle;

    protected StateLayout mStateLayout;

    protected LinearLayout mToolbarLayout;

    private String rightTitleTxt;
    protected int titleTxt;

    private MProgressDialog dialog;


    private static final String TAG = "BaseActivity";

    private BaseNetStateChangeReceiver receiver;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.context = getApplicationContext();
        dialog = new MProgressDialog(mContext, new MProgressDialog.Builder(mContext));
        ActivityFeature activityFeature = BaseActivity.this.getClass().getAnnotation(ActivityFeature.class);
        if (activityFeature == null) {
            throw new IllegalArgumentException(">>> not set layout resources!");
        }
        setContentView(activityFeature.layout());
        //=====================防止控制针异常，不用butterknife==========//
        mStateLayout = findViewById(R.id.state_layout);
        mToolbar = findViewById(R.id.toolbar);
        mTitleView = findViewById(R.id.title);
        rightTitle = findViewById(R.id.txt_right);
        mToolbarLayout = findViewById(R.id.mToolbarLayout);
        //=====================防止控制针异常，不用butterknife==========//
        ButterKnife.bind(this);
        rightTitleTxt = activityFeature.rightTitleTxt();
        titleTxt = activityFeature.titleTxt();
        if (mToolbar != null) {
            if (activityFeature.toolbarArrow() > 0) {
                mToolbar.setNavigationIcon(activityFeature.toolbarArrow());
            } else {
                mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
            }
        }
        initToolbar();
        setStateBar();
        initData();
        initPresenter();
        initView(savedInstanceState);
        if (!ActivityUtils.checkSignature(getIntent())) {
            throw new ActivityNotFoundException("405");
        }

        receiver = getReceiver();
        registerReceiver(this);
    }

    protected BaseNetStateChangeReceiver getReceiver() {
        return null;
    }

    private void registerReceiver(Context context) {
        if (receiver != null) {
            IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public void setStateBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.base_white), 0);
    }

    @Nullable
    public abstract View.OnClickListener setRightViewClickListener();

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    /**
     * 初始化视图
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 获取数据
     */
    public abstract void initData();

    /**
     * 返回操作
     */
    public abstract void BackPressed();

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
            //设置title
            if (titleTxt > 0)
                mTitleView.setText(titleTxt);
            //设置右边按钮
            if (rightTitle != null) {
                if (!rightTitleTxt.equals(Config.TOOLBAR_RIGHT_DEFAULT)) {
                    rightTitle.setVisibility(View.VISIBLE);
                    rightTitle.setText(rightTitleTxt);
                    if (setRightViewClickListener() != null)
                        rightTitle.setOnClickListener(setRightViewClickListener());
                } else {
                    rightTitle.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            BackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        BackPressed();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }


    public void start(Class clazz) {
        Intent starter = new Intent(this, clazz);
        startActivity(ActivityUtils.signature(starter));
    }

    public void start(Intent intent) {
        startActivity(ActivityUtils.signature(intent));
    }

    public void startForResult(Class clazz, int requestCode) {
        Intent starter = new Intent(this, clazz);
        startActivityForResult(ActivityUtils.signature(starter), requestCode);
    }

    public void startForResult(Intent intent, int requestCode) {
        startActivityForResult(ActivityUtils.signature(intent), requestCode);
    }

    /**
     * 加载弹窗
     */
    public void showLoadingDialog() {
        dialog.show();
    }

    /**
     * 取消弹窗
     */
    public void loadingDialogDismiss() {
        dialog.dismiss();
    }

    /**
     * 吐司
     *
     * @param txt
     */
    public void showToast(String txt) {
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
