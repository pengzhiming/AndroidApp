package com.yl.core.component.net;

import java.util.HashMap;

/**
 * Created by zm on 2018/9/9.
 */

public class RequestManager {

    protected static HashMap<Class, Object> sRequestManager = new HashMap<>();

    public static <T> T getRequest(Class<T> clazz, String baseUrl) {
        T t = (T) sRequestManager.get(clazz);
        if (t == null) {
            t = RetrofitClient.createApi(clazz, baseUrl);
            sRequestManager.put(clazz, t);
        }
        return t;
    }
}
