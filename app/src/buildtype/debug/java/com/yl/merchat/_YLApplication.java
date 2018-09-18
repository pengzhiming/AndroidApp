package com.yl.merchat;

import com.facebook.stetho.Stetho;

/**
 * Created by zm on 2018/9/10.
 */
public class _YLApplication extends YLApplication{

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    private void init() {
        /**
         * chrome://inspect
         * 可查看本地数据库 对网络请求进行抓包
         */
        Stetho.initializeWithDefaults(this);
    }

}
