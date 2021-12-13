package com.dongyuwuye.component_net;


import com.dongyuwuye.compontent_base.BaseResp;
import com.dongyuwuye.compontent_sdk.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import retrofit2.HttpException;

/**
 * create by：mc on 2018/3/29 15:33
 * <网络异常处理>
 * email：
 */
public class ExceptionHelper {

    private static String errorMsg = "请求失败，请稍后重试";

    public static String handleException(Throwable e) {
        e.printStackTrace();
        if (e instanceof SocketTimeoutException) {//网络超时
            errorMsg = "网络连接异常";

        } else if (e instanceof ConnectException) { //均视为网络错误
            errorMsg = "网络连接异常";

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //均视为解析错误
            errorMsg = "数据解析异常";
        } else if (e instanceof UnknownHostException) {
            errorMsg = "网络连接异常";
        } else if (e instanceof ServiceException) { //服务器返回的异常
            errorMsg = e.getMessage();
        } else if (e instanceof IllegalArgumentException) {
            errorMsg = "参数错误";
        } else if (e instanceof DataException) {
            errorMsg = ((DataException) e).getMsg();
        } else if (e instanceof HttpException) {
            try {
                String error = ((HttpException) e).response().errorBody().string();
                BaseResp resp = new Gson().fromJson(error, BaseResp.class);
                errorMsg = StringUtils.isNullOrEmpty(resp.getMsg()) ? resp.getMessage() : resp.getMsg();
                if (StringUtils.isNullOrEmpty(errorMsg)) {
                    errorMsg = error;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {//未知错误
            errorMsg = "未知错误，可能抛锚了吧~";
        }
        return errorMsg;
    }
}
