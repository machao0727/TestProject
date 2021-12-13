package com.dongyuwuye.compontent_base;

import java.util.List;

/**
 * create by：mc on 2018/5/17 11:42
 * <p>
 * email：
 */
public interface IListView extends IBaseView {
    /**
     * 刷新完成
     *
     * @param dataItems 数据集
     * @param moreData  是否有更多数据
     */
    void complete(List<Object> dataItems, boolean moreData);

}

