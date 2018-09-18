package com.yl.core.component.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.yl.core.R;
import com.yl.core.component.image.BaseImageLoaderStrategy;
import com.yl.core.component.image.ImageLoaderConfig;
import com.yl.core.component.image.LoaderListener;
import com.yl.core.component.image.util.ImageUtil;
import com.yl.core.component.log.DLog;


import java.io.File;

/**
 * Glide 加载器，当前工程使用Glide 作为图片Loader
 * 全局网络图片加载 请使用 {@link com.yl.core.component.image.ImageLoader}
 * <p>
 * 如需要使用其他图片加载引擎 ， 请自定义ImageLoaderStrategy 并指定 {@link  com.yl.core.component.image.ImageLoader#getLoader()}
 *  Created by zm on 2018/9/9.
 */

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {

    // default config
    public final static ImageLoaderConfig defaultConfigBuilder = new ImageLoaderConfig.Builder().
            setCropType(ImageLoaderConfig.CENTER_INSIDE).
            setAsBitmap(true).
            setPlaceHolderResId(R.drawable.bg_image_placeholder).
            setErrorResId(R.drawable.bg_image_placeholder).
            setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
            setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();

    private GlideImageLoaderStrategy() { }

    public static GlideImageLoaderStrategy getInstance() {
        return LazyHolder.strategy;
    }

    private static class LazyHolder {
        public static final GlideImageLoaderStrategy strategy = new GlideImageLoaderStrategy();
    }

    private static void setListener(GenericRequestBuilder request, final LoaderListener listener) {
        request.listener(new RequestListener<Object, Bitmap>() {
            @Override
            public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                listener.onError();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                listener.onSuccess(resource);
                DLog.d("setListener -> onException");
                return false;
            }
        });
    }

    @Override
    public void loadImage(final ImageView imageView, String imageUrl, ImageLoaderConfig config, LoaderListener listener) {
        final Context context = imageView.getContext();
        if (null == config) {
            config = defaultConfigBuilder;
        }
        try {
            GenericRequestBuilder builder = null;
            if (config.isAsGif()) {
                //gif类型
                GifRequestBuilder request = Glide.with(context).load(imageUrl).asGif();
                if (config.getCropType() == ImageLoaderConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isAsBitmap()) {
                //bitmap 类型
                BitmapRequestBuilder request = Glide.with(context).load(imageUrl).asBitmap();
                if (config.getCropType() == ImageLoaderConfig.CENTER_CROP) {
                    request.centerCrop();
                } else if (config.getCropType() == ImageLoaderConfig.CENTER_INSIDE) {//实现centerInside效果(imageview scaletype设置后，此处要使用request.dontTransform设置才生效)
                    request.dontTransform();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (config.isCrossFade()) {
                // 渐入渐出动画
                DrawableRequestBuilder request = Glide.with(context).load(imageUrl).crossFade();
                if (config.getCropType() == ImageLoaderConfig.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }

            //缓存设置
            builder.diskCacheStrategy(config.getDiskCacheStrategy().getStrategy()).
                    skipMemoryCache(config.isSkipMemoryCache()).
                    priority(config.getPriority().getPriority());
            builder.dontAnimate();
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }

            if (null != listener) {
                setListener(builder, listener);
            }
            if (0 != config.getErrorResId()) {
                builder.error(config.getErrorResId());
            }
            if (0 != config.getPlaceHolderResId()) {
                builder.placeholder(config.getPlaceHolderResId());
            }
            if (null != config.getSize()) {
                builder.override(config.getSize().getWidth(), config.getSize().getHeight());
            }

            // 圆角处理
            if (config.isCropCircle()) {
                builder.into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(360);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
            } else {
                builder.into(imageView);
            }

            /**
             * 支持输出到chrome的log仅有debug 和 beta的版本才有
             * {@link JApplication_BuildType#initLog()}
             * 打印丢到加载之后，防止beta 版本输出log到chrome导致界面卡顿
             */
            if (null == imageUrl) {
                DLog.d("imageUrl is null");
            } else {
                DLog.d("loadImage -> " + imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (imageView != null && config != null && config.getErrorResId() != 0) {
                imageView.setImageResource(config.getErrorResId());
            }
        }
    }

    /**
     * SD卡资源："file://"+ Environment.getExternalStorageDirectory().getPath()+"/test.jpg"<p/>
     * assets资源："file:///android_asset/f003.gif"<p/>
     * raw资源："Android.resource://com.frank.glide/raw/raw_1"或"android.resource://com.frank.glide/raw/"+R.raw.raw_1<p/>
     * drawable资源："android.resource://com.frank.glide/drawable/news"或load"android.resource://com.frank.glide/drawable/"+R.drawable.news<p/>
     * ContentProvider资源："content://media/external/images/media/139469"<p/>
     * http资源："http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg"<p/>
     * https资源："https://img.alicdn.com/tps/TB1uyhoMpXXXXcLXVXXXXXXXXXX-476-538.jpg_240x5000q50.jpg_.webp"<p/>
     *
     * @param imageView
     * @param imageUrl
     */
    @Override
    public void loadImage(ImageView imageView, String imageUrl) {
        loadImage(imageView, imageUrl, defaultConfigBuilder, null);
    }

    @Override
    public void loadImage(ImageView imageView, String imageUrl, String thumbnailUrl) {

    }

    @Override
    public void loadRoundedImage(ImageView imageView, String imageUrl) {
        ImageLoaderConfig roundConfigBuilder = new ImageLoaderConfig.Builder().
                setCropType(ImageLoaderConfig.CENTER_CROP).
                setAsBitmap(true).
                setCropCircle(true).
                setPlaceHolderResId(R.drawable.bg_image_placeholder_round).
                setErrorResId(R.drawable.bg_image_placeholder).
                setDiskCacheStrategy(ImageLoaderConfig.DiskCache.SOURCE).
                setPrioriy(ImageLoaderConfig.LoadPriority.HIGH).build();
        loadImage(imageView, imageUrl, roundConfigBuilder, null);
    }

    @Override
    public void loadGif(ImageView imageView, String imageUrl) {
    }

    @Override
    public void downloadImage(final Context context, final Handler handler, final String uri, final String savePath, final String name, final boolean isInsertMedia) {
        Glide.with(context.getApplicationContext())
                .load(uri)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        ImageUtil.saveImageAndRefresh(context, handler, BitmapFactory.decodeFile(resource.getPath()), uri, savePath, name, isInsertMedia);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (handler != null) {
                            handler.sendEmptyMessage(ImageUtil.SAVE_IMAGE_FAIL);
                        }
                    }
                });
    }

    @Override
    public void downloadImage(final Context context, final Handler handler, final String uri,
                              final String savePath, final String name, final boolean isInsertMedia,
                              final LoaderListener loaderListener) {
        Glide.with(context.getApplicationContext())
                .load(uri)
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        Bitmap bitmap =  BitmapFactory.decodeFile(resource.getPath());
                        ImageUtil.saveImageAndRefresh(context, handler, bitmap, uri, savePath, name, isInsertMedia);
                        loaderListener.onSuccess(bitmap);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (handler != null) {
                            handler.sendEmptyMessage(ImageUtil.SAVE_IMAGE_FAIL);
                        }
                        loaderListener.onError();
                    }
                });
    }
}
