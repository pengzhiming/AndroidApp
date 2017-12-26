package com.core.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 关闭Closeable对象工具类
 *
 * Created by zm on 2017/10/18.
 */

public final class CloseUtils {

    private CloseUtils() {
    }

    /**
     * 关闭Closeable对象
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
