package com.yl.core.component.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.yl.core.YLCore;
import com.yl.core.component.net.util.NetWorkUtils;
import com.yl.core.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 当前项目使用retrofix 作为http请求引擎
 * <p>
 * Created by zm on 2018/9/9.
 */

public class RetrofitClient {

    public static class BadDoubleDeserializer implements JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            try {
                return Double.parseDouble(element.getAsString().replace(',', '.'));
            } catch (NumberFormatException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static class BadIntDeserializer implements JsonDeserializer<Integer> {
        @Override
        public Integer deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
            try {
                return Integer.parseInt(element.getAsString().substring(0, element.getAsString().indexOf(".")));
            } catch (NumberFormatException e) {
                throw new JsonParseException(e);
            }
        }
    }

    public static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient())
                .build();
        return builder.create(clazz);
    }

    private static OkHttpClient getClient() {

        /**
         * 使用自定义logInterceptor 支持 chrome输出，在debug 或 beta版本
         */
        JHttpLoggingInterceptor logInterceptor = new JHttpLoggingInterceptor();
        logInterceptor.setLevel(JHttpLoggingInterceptor.Level.BODY);

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
        SSL.SSLParams sslParams = SSL.getSslSocketFactory(null, null, null);

        /**
         * ok give me five
         */
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cache(cache);

        if(SystemUtil.isCanLog()) {
            okHttpClient.addInterceptor(logInterceptor);
        }

        return okHttpClient.build();
    }

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;

    /*************************缓存设置*********************/
   /*
    1. noCache 不使用缓存，全部走网络

    2. noStore 不使用缓存，也不存储缓存

    3. onlyIfCached 只使用缓存

    4. maxAge 设置最大失效时间，失效则不使用 需要服务器配合

    5. maxStale 设置最大失效时间，失效则不使用 需要服务器配合

    6. minFresh 设置有效时间，依旧如上

    7. FORCE_NETWORK 只走网络

    8. FORCE_CACHE 只走缓存
    */

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    protected static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetConnected(YLCore.sContext)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetWorkUtils.isNetConnected(YLCore.sContext)) {
                    //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }
}
