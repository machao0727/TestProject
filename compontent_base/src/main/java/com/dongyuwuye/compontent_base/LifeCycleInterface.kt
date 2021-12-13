package com.dongyuwuye.compontent_base

import com.trello.rxlifecycle3.LifecycleTransformer

/**
 * create by：mc on 2018/5/15 10:45
 * <基类view接口></基类view接口>，所有其他页面接口均需继承>
 * email：
 */
interface LifeCycleInterface {
    fun <T> bindToLifecycle(): LifecycleTransformer<T>?
}