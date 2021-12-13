package com.dongyuwuye.compontent_base;

import com.trello.rxlifecycle3.LifecycleTransformer;

/**
 * create by：mc on 2018/5/15 10:45
 * <基类view接口，所有其他页面接口均需继承>
 * email：
 */
public interface IBaseView {
    /**
     * 展示网络错误页面
     */
    void showError();

    /**
     * 展示数据为空页面
     */
    void showEmpty();

    /**
     * 展示内容页面
     */
    void showContent();

    /**
     * 展示正在加载
     */
    void showLoading();

    /**
     * 展示吐司
     */
    void showText(String text);

    <T> LifecycleTransformer<T> bindToLifecycle();
}
