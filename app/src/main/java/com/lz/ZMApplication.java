package com.lz;

import android.content.Context;

import com.zm.lib.LibApplication;

/**
 * Created by zm on 2017/7/26.
 */

public class ZMApplication extends LibApplication {

    private static  Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
