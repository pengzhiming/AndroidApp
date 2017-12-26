package com.core.component.net.exception;

/**
 * Created by zm on 2017/11/16.
 */

public class ApiException extends Exception {

  public int status;
  public int code;
  public String message;
  public Object object;

  public ApiException(Throwable throwable, int code) {
    super(throwable);
    this.code = code;
    if (throwable != null && throwable instanceof ServerException) {
      object = ((ServerException) throwable).object;
    }
  }

  public ApiException(String msg) {
    super(msg);
  }
}
