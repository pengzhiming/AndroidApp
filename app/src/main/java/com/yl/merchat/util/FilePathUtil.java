package com.yl.merchat.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by zm on 2018/9/10.
 */

public class FilePathUtil {

    private static String getBaseDir(String childDir) {
        return Environment.getExternalStorageDirectory().getPath() + "/yyl/" + childDir;
    }

    public static String getInvoicePdfDir() {
        return checkAndMkdirs(getBaseDir("cache/invoice/"));
    }

    public static String getCacheCrop() {
        return checkAndMkdirs(getBaseDir("cache/crop/"));
    }

    public static String getCacheImage() {
        return checkAndMkdirs(getBaseDir("cache/image/"));
    }


    public static String getCacheImagePick() {
        return checkAndMkdirs(getBaseDir("cache/image/")) + System.currentTimeMillis() + ".jpg";
    }

    public static String getCacheWeb() {
        return checkAndMkdirs(getBaseDir("cache/web/"));
    }

    public static String getCache() {
        return checkAndMkdirs(getBaseDir("cache/"));
    }

    public static String getImage() {
        return checkAndMkdirs(getBaseDir("image/"));
    }

    public static String getAdImage() {
        return checkAndMkdirs(getBaseDir("image/ad/"));
    }

    /**
     * 检查文件夹是否存在
     *
     * @param dir
     * @return
     */
    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (file.exists() == false) {
            file.mkdirs();
        }
        return dir;
    }
}
