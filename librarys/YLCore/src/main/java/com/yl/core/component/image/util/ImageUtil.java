package com.yl.core.component.image.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.yl.core.component.log.DLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 *  Created by zm on 2018/9/9.
 */

public class ImageUtil {

    /**
     * 保存图片成功
     */
    public static final int SAVE_IMAGE_SUCCESS = 2000;

    /**
     * 保存图片失败
     */
    public static final int SAVE_IMAGE_FAIL = 2002;
    /**
     * 图片质量，100表示最高
     */
    private static final int BITMAP_QUALITY = 95;

    /**
     * 保存网络下载的图片到指定地址 地址为{@link FilePathUtil#getImage()}
     * @param context
     * @param handler
     * @param bitmap
     * @param url
     * @param name
     */
    public static void saveImage(Context context, Handler handler, Bitmap bitmap, String url, String name) {
        saveImageAndRefresh(context, handler, bitmap, url, null, name, false);
    }

    /**
     * 保存网络下载的图片到指定地址，并插入相册可见
     * @param context
     * @param handler
     * @param bitmap
     * @param url
     * @param name
     */
    public static void saveImageAndRefresh(Context context, Handler handler, Bitmap bitmap, String url, String savePath, String name, boolean isRefresh) {
        if (bitmap != null) {
            String path;
            // 有sd写入到sd
            if (FileUtil.isExistSdcard()) {
                path = saveBitmapToSDcard(context, bitmap, url, savePath, null, isRefresh);
            }
            // 无sd写入到相册
            else {
                path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, name, "");
            }
            if (path != null) {
                // 通知相册刷新
                if (isRefresh) {
                    if (!FileUtil.isVersionUpKitkat()) {
                        try {
                            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                        } catch (SecurityException e) {
                            MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, new String[]{"image/*"}, null);
                        }
                    } else {
                        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, new String[]{"image/*"}, null);
                    }
                }
                if (handler != null) {
                    handler.sendEmptyMessage(SAVE_IMAGE_SUCCESS);
                }
            }
        } else {
            if (handler != null) {
                handler.sendEmptyMessage(SAVE_IMAGE_FAIL);
            }
        }
    }

    /**
     * 保存网络图片 bitmap 到文件
     *
     * @param context
     * @param bm        不能为空
     * @param url       不能为空
     * @param savePath  为空则默认{@link FilePathUtil#getImage}
     * @param name      文件名为空则 使用url哈希化为文件名
     * @param isRefresh
     * @return
     */
    public static String saveBitmapToSDcard(Context context, Bitmap bm, String url, String savePath, String name, boolean isRefresh) {
        if (bm == null || TextUtils.isEmpty(url)) {
            return null;
        }

        if (TextUtils.isEmpty(savePath)) {
            savePath = FilePathUtil.getImage();
        }

        String filename = TextUtils.isEmpty(name) ? FileUtil.convertUrlToFileName(url) : name;
        File file = new File(FileUtil.checkAndMkdirs(savePath) + filename);
        if (file.exists()) {
            DLog.e("save bitmap but file exists");
            return file.getAbsolutePath();
        }
        try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, BITMAP_QUALITY, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            DLog.e("FileNotFoundException" + (e == null ? "" : e.getMessage()));
        } catch (IOException e) {
            DLog.e("IOException" + (e == null ? "" : e.getMessage()));
        }

        if (isRefresh) {
            ContentValues values = new ContentValues(7);
            values.put(MediaStore.Images.Media.TITLE, filename);
            values.put(MediaStore.Images.Media.TITLE, filename);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, file.getAbsolutePath());
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            ContentResolver contentResolver = context.getContentResolver();

            try {
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }

    public static Bitmap getAdCacheImage(String uri) {
        File file = FileUtil.getAdUrlCacheFile(uri);
        if(file != null){
            return BitmapFactory.decodeFile(file.getPath());
        }
        return null;
    }
}
