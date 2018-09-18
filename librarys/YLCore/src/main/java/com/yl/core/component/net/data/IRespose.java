package com.yl.core.component.net.data;

/**
 * 返回数据基类必须实现接口
 * 提供结果判断 和 通用消息返回
 * <P>
 * Created by zm on 2018/9/9.
 *
 */
public interface IRespose {

    boolean isSuccess() ;

    String getMessage();

    int getICode();
}
