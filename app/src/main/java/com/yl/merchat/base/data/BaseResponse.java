package com.yl.merchat.base.data;

import com.yl.core.component.net.data.IRespose;

import java.io.Serializable;

/**
 * 通用网络请求回复类型
 * Created by zm on 2018/9/9.
 */

public class BaseResponse<T> implements Serializable, IRespose {

  /**
   * 成功正常
   */
  public static String SUCCESS_CODE_SUCCESS = "0";
  /**
   * 自定义异常
   */
  public static String SUCCESS_CODE_ERROR_EXPECTION = "1";
  /**
   * 服务器异常
   */
  public static String SUCCESS_CODE_ERROR_SERVER = "2";
  /**
   * 应用停止使用
   */
  public static String SUCCESS_CODE_ERROR_OVER = "3";
  /**
   * 建议更新
   */
  public static String SUCCESS_CODE_UPGRADE = "98";
  /**
   * 强制更新
   */
  public static String SUCCESS_CODE_UPGRADE_FOUCE = "99";

  protected String message;

  protected int code;

  protected T data;

  @Override
  public boolean isSuccess() {
    return 200 == code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public int getICode() {
    return code;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
