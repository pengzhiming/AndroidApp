package com.yl.merchat;

import android.app.Application;
import android.content.Context;

import com.yl.core.YLCore;

/**
 * Created by zm on 2018/9/8.
 */
public class YLApplication extends Application{

    /**
     * Application Context
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        sContext = this.getApplicationContext();

        // 初始化核心库
        YLCore.init(this);
    }

    /**
     * 获取全局Context
     * @return context
     */
    public static Context getContext() {
        return sContext;
    }
}
