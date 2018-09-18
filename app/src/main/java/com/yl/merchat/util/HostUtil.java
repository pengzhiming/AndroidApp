package com.yl.merchat.util;

import com.yl.merchat.BuildConfig;

/**
 *
 * 提供API_HOST
 *
 * Created by zm on 2018/9/9.
 */
public class HostUtil {

    /**
     * {@link config.gradle#host}
     */
    private static final String HOST_URL = BuildConfig.API_HOST;


    public static String getServerHost() {
        return HOST_URL;
    }
}
