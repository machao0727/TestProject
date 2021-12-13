package com.dongyuwuye.component_net;


import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;


import com.dongyuwuye.compontent_widget.progressdialog.MProgressDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * create by：mc on 2018/3/29 16:16
 * <结果回调封装>
 * email：
 */
public abstract class RxCallBack<T> implements Observer<T> {

    private Context mContext;
    private String showTxt = "加载中...";
    private MProgressDialog dialog;

    public RxCallBack() {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public RxCallBack(Context context) {
        mContext = context;
        dialog = new MProgressDialog(mContext, new MProgressDialog.Builder(mContext));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public RxCallBack(Context context, String showTxt) {
        dialog = new MProgressDialog(mContext, new MProgressDialog.Builder(mContext));
        this.showTxt = showTxt;
    }

    @Override
    public void onSubscribe(Disposable d) {
//        DisposableUtils.getInstance().addDisposable(d);
        if (dialog != null) {
            if (!TextUtils.isEmpty(showTxt)) {
                dialog.show(showTxt);
            } else {
                dialog.showNoText();
            }
        }
    }

    @Override
    public void onNext(T value) {
        _onNext(value);
        onComplete();
    }

    @Override
    public void onError(Throwable e) {
//        if (e instanceof ServiceException) {
//            if (((ServiceException) e).getCode() == Config.NOT_LOGIN) {//token错误,跳登陆页,先清空栈内的activity保证登陆activity为栈底
//                UserDaoUtils.deleteToken();
//                Intent intent = new Intent(ApplicationUtils.application, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                ApplicationUtils.application.startActivity(intent);
//            }
//        }
        _onError(ExceptionHelper.handleException(e));
        onComplete();
    }

    @Override
    public void onComplete() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }


    public abstract void _onNext(T value);

    public abstract void _onError(String e);
}
