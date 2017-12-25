package com.core.component.net.data;

/**
 * 返回数据基类必须实现接口
 * 提供结果判断 和 通用消息返回
 * Created by zm on 2017/11/17.
 *
 */
public interface IRespose {

    boolean isSuccess() ;

    String getMessage();

    int getIStatus();
}
