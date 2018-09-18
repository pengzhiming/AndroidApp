package com.yl.merchat.component.net;

import com.yl.merchat.component.net.interceptor.CommonInterceptor;
import com.yl.merchat.component.net.interceptor.YLHttpLoggingInterceptor;
import com.yl.core.YLCore;
import com.yl.core.component.net.JHttpLoggingInterceptor;
import com.yl.core.component.net.RetrofitClient;
import com.yl.core.component.net.SSL;
import com.yl.core.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zm on 2018/9/9.
 */

public class YLRetrofitClient extends RetrofitClient {

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient(baseUrl))
                .build();
        return builder.create(clazz);
    }

    private static OkHttpClient getClient(String baseUrl) {

        /**
         * 使用自定义logInterceptor 支持 chrome输出，在debug 或 beta版本
         */
        YLHttpLoggingInterceptor logInterceptor = new YLHttpLoggingInterceptor();
        logInterceptor.setLevel(JHttpLoggingInterceptor.Level.BODY);

        /**
         * 公共参数
         */
        CommonInterceptor commonInterceptor = new CommonInterceptor();

        /**
         * 本地缓存
         */
        File cacheFile = new File(YLCore.sContext.getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);

        /**
         * 请求缓存策略
         */
        Interceptor mRewriteCacheControlInterceptor = getInterceptor();

        /**
         * ssl
         */
        SSL.SSLParams sslParams = null;

        try {
            sslParams = SSL.getSslSocketFactory(CertificateProvider.getCertificateStreams(baseUrl), null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * ok give me five
         */
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(commonInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cache(cache);

        if (SystemUtil.isCanLog()) {
            okHttpClient.addInterceptor(logInterceptor);
        }
        return okHttpClient.build();
    }

}
