package com.dongyuwuye.compontent_base;

/**
 * create by：mc on 2018/5/15 10:51
 * <presenter 基类，所有其他Presenter均继承于此>
 * email：
 */
public interface IBasePresenter {
    /**
     * 刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();

}
