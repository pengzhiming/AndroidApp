package com.yl.merchat.base.data;

import okhttp3.RequestBody;

/**
 * 部分接口采用json post 的形式 发起网络请求
 * <p>
 * 项目中所有的post 接口使用RequestBody的形式发送数据
 * <p>
 * 所有改类型数据必须实现此接口
 * <p>
 * Created by zm on 2018/9/9.
 */

public interface IRequestBody<T> {

    RequestBody toRequestBody();
}
