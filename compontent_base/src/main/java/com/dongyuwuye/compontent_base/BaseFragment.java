package com.dongyuwuye.compontent_base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dongyuwuye.compontent_base.annotation.FragmentFeature;
import com.dongyuwuye.compontent_sdk.utils.ActivityUtils;
import com.trello.rxlifecycle3.components.support.RxFragment;

import androidx.annotation.Nullable;
import butterknife.ButterKnife;

/**
 * Created by mc on 2018/3/28.
 * 基类fragment
 */

public abstract class BaseFragment extends RxFragment {
    public BaseActivity activity;
    protected View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        FragmentFeature fragmentFeature = this.getClass().getAnnotation(FragmentFeature.class);
        if (fragmentFeature == null) {
            throw new IllegalArgumentException(">>> not set layout resources!");
        }
        view = inflater.inflate(fragmentFeature.layout(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewForKt();
    }

    /**
     * 初始化presenter
     */
    protected abstract void initPresenter();

    public abstract void initView();

    public void initViewForKt() {
    }

    public abstract void load();

    /**
     * 吐司
     *
     * @param txt
     */
    public void showToast(String txt) {
        Toast.makeText(activity, txt, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void startForResult(Class clazz, int requestCode) {
        Intent starter = new Intent(activity, clazz);
        startActivityForResult(ActivityUtils.signature(starter), requestCode);
    }

    public void startForResult(Intent intent, int requestCode) {
        startActivityForResult(ActivityUtils.signature(intent), requestCode);
    }
}
