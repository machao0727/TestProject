package com.dongyuwuye.component_net;


import com.dongyuwuye.compontent_base.BaseResp;
import com.dongyuwuye.compontent_base.LifeCycleInterface;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * create by：mc on 2018/3/29 15:45
 * <返回数据外层code预处理>
 * email：
 */
public class RxHelper {

    /**
     * Rx优雅处理服务器返回
     * 仅对正确结果进行处理,其余均视为服务器错误
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResp<T>, T> handleResult() {
        return upstream -> upstream.flatMap(result -> {
                    if (result.getCode() == 200) {
                        return createData(result.getData());
                    } else {
                        return Observable.error(new ServiceException(result.getMsg(), result.getCode()));
                    }
                }
        ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Rx优雅处理服务器返回
     * 仅对正确结果进行处理,其余均视为服务器错误
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> handleResult2() {
        return upstream -> upstream.flatMap(result -> {
                    if (result instanceof BaseResp) {
                        if (((BaseResp) result).getCode() == 200) {
                            return createData(result);
                        } else {
                            return Observable.error(new ServiceException(((BaseResp) result).getMsg(), ((BaseResp) result).getCode()));
                        }
                    } else {
                        return createData(result);
                    }
                }
        ).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
