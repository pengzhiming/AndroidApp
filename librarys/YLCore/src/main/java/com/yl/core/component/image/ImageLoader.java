package com.yl.core.component.image;

import android.content.Context;
import android.widget.ImageView;

import com.yl.core.component.image.glide.GlideImageLoaderStrategy;


/**
 *  Created by zm on 2018/9/9.
 */

public class ImageLoader {

    /**
     * 指定当前图片加载器 Glide
     * @return
     */
    protected static BaseImageLoaderStrategy getLoader() {
        return GlideImageLoaderStrategy.getInstance();
    }

    /**
     * 自定义加载
     * @param imageView
     * @param imageUrl
     * @param imageLoaderConfig
     * @param listener
     */
    public static void loadImage(ImageView imageView, String imageUrl, ImageLoaderConfig imageLoaderConfig, LoaderListener listener) {
        getLoader().loadImage(imageView, imageUrl, imageLoaderConfig, listener);
    }

    /**
     * 加载基础图片
     * @param imageView
     * @param imageUrl
     */
    public static void loadImage(ImageView imageView, String imageUrl){
        getLoader().loadImage(imageView, imageUrl);
    }

    /**
     * 先加载缩略图，再加载大图
     * @param imageView
     * @param imageUrl
     * @param thumbnailUrl
     */
    public static void loadImage(ImageView imageView, String imageUrl, String thumbnailUrl){
        getLoader().loadImage(imageView, imageUrl, thumbnailUrl);
    }

    /**
     * 加载圆图
     * @param imageView
     * @param imageUrl
     */
    public static void loadRoundedImage(ImageView imageView, String imageUrl){
        getLoader().loadRoundedImage(imageView, imageUrl);
    }


    /**
     * 加载Gif
     * @param imageView
     * @param imageUrl
     */
    public static void loadGif(ImageView imageView, String imageUrl) {
        getLoader().loadGif(imageView, imageUrl);
    }

    /**
     * 下载网络图片并保存
     * @param context
     * @param uri
     */
    public static void downloadImage(Context context, String uri, String savePath) {
        getLoader().downloadImage(context, null, uri, savePath, null, false);
    }

    /**
     *下载网络图片并保存
     *
     * @param context
     * @param uri
     * @param savePath
     * @param listener
     */
    public static void downloadImage(Context context, String uri, String savePath, LoaderListener listener) {
        getLoader().downloadImage(context, null, uri, savePath, null, false, listener);
    }
}


