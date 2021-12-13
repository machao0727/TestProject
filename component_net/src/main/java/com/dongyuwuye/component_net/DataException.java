package com.dongyuwuye.component_net;

import java.io.IOException;

/**
 * create by：mc on 2018/8/17 11:09
 * email:
 * 用于接收服务器返回空数据的时候的异常
 */
public class DataException extends IOException {
    private String msg;
    private int code;

    public DataException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public DataException(String message, String msg, int code) {
        super(message);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
