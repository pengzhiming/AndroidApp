package com.yl.merchat.base.data;

import com.google.gson.Gson;

import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 通用网络请求
 * <p>
 * Created by zm on 2018/9/9.
 */

public class BaseRequestBody<T> implements Serializable, IRequestBody {

    private T body;

    public BaseRequestBody() {
        this(null);
    }

    public BaseRequestBody(T body) {
        this.body = body;
    }

    public void setBody(T body) {
        this.body = body;
    }


    @Override
    public RequestBody toRequestBody() {
        String json = new Gson().toJson(this.body);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return body;
    }
}
