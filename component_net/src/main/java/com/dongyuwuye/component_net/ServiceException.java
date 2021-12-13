package com.dongyuwuye.component_net;

/**
 * create by：mc on 2018/3/29 15:50
 * <网络异常>
 * email：
 */
public class ServiceException extends Throwable {

    private String msg;
    private int code;

    public ServiceException(String msg, int code) {
        super(msg);
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
