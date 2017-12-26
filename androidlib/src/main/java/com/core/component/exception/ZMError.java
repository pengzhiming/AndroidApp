package com.core.component.exception;

/**
 * Created by zm on 2017/10/27.
 */

public class ZMError extends RuntimeException{

    private int errorCode;

    private String errorMessage;

    public ZMError(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
