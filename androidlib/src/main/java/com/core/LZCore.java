package com.core;

import android.content.Context;

import com.core.component.net.SSL;

/**
 * Created by zm on 2017/11/17.
 */

public class LZCore {

    public static Context sContext;

    public static void init(Context context) {
        sContext = context.getApplicationContext();
        SSL.init();
    }
}
