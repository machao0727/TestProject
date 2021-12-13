package com.dongyuwuye.compontent_base;

/**
 * create by
 * email:
 */
public class BaseResp<T> {
    private T result;
    private boolean success;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
