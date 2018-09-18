package com.yl.merchat.component.exception;

/**
 * 自定义异常
 * <p>
 * Created by zm on 2018/9/11.
 */
public class YLError extends RuntimeException{
    private String code;
    private String message;

    public YLError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
