package com.yl.core.component.net.exception;

import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

/**
 * Created by zm on 2018/9/9.
 */

public class ExceptionEngine {

    private static final String ERROR_FORBIDDEN = "登录已过期，请重新登录";
    private static final String ERROR_SERVER_TRYAGAIN = "遇到错误了，请稍后再试";
    private static final String ERROR_CONNET_TRYAGAIN = "网络不太好，请检查网络再试试";

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        ApiException ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ApiException(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                    /**
                     * 1权限错误
                     */
                    ex = new ApiException(e, ERROR.PERMISSION_ERROR);
                    ex.message = ERROR_FORBIDDEN;
                    return ex;
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                    /**
                     * 2网络错误
                     */
                    ex = new ApiException(e, ERROR.NETWORD_ERROR);
                    ex.message = ERROR_CONNET_TRYAGAIN;
                    break;
                case NOT_FOUND:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    /**
                     * 2网络错误
                     */
                    ex.message = ERROR_SERVER_TRYAGAIN;
                    break;
            }
            return ex;
        }
        /**
         * 3连接异常
         */
        else if (e instanceof ConnectException || e instanceof SocketTimeoutException) {
            ex = new ApiException(e, ERROR.NETWORD_ERROR);
            ex.message = ERROR_CONNET_TRYAGAIN;
            return ex;
        }
        /**
         * 4服务器返回错误
         */
        else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ApiException(resultException, ERROR.RETURN_ERROR);
            /**
             * 无权限
             */
            if(resultException.isForbidden()) {
                ex.message = TextUtils.isEmpty(resultException.message) ? ERROR_FORBIDDEN : resultException.message;
            } else {
                ex.message = TextUtils.isEmpty(resultException.message) ? ERROR_SERVER_TRYAGAIN : resultException.message;
            }
            ex.status = resultException.code;
            return ex;
        }
        /**
         * 5解析错误
         */
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR);
            ex.message = ERROR_SERVER_TRYAGAIN;
            return ex;
        }
        /**
         * 6未知错误
         */
        else {
            ex = new ApiException(e, ERROR.UNKNOWN);
            ex.message = e == null || TextUtils.isEmpty(e.getMessage()) ? ERROR_SERVER_TRYAGAIN : e.getMessage();
            return ex;
        }
    }

    /**
     * 错误
     */
    public static class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;
        /**
         * 权限
         */
        public static final int PERMISSION_ERROR = 1004;
        /**
         * 返回结果为错误
         */
        public static final int RETURN_ERROR = 1005;
    }
}
