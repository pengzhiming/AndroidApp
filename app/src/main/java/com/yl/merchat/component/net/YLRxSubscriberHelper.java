package com.yl.merchat.component.net;


import android.content.Context;

import com.yl.merchat.base.data.BaseResponse;
import com.yl.merchat.util.ThrowableUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.net.JHttpLoggingInterceptor;
import com.yl.core.component.net.RxSubscriberHelper;
import com.yl.core.component.net.exception.ApiException;
import com.yl.core.component.net.exception.ExceptionEngine;

import java.io.IOException;
import java.util.List;

import io.reactivex.exceptions.CompositeException;
import retrofit2.HttpException;

/**
 * Created by zm on 2018/9/9.
 */

public abstract class YLRxSubscriberHelper<T> extends RxSubscriberHelper<T> {

    public YLRxSubscriberHelper() {
        super();
    }


    public YLRxSubscriberHelper(Context context, boolean isShowLoad) {
        super(context, isShowLoad);
    }

    /**
     * @param context
     * @param builder 配置builder
     */
    public YLRxSubscriberHelper(Context context, Builder builder) {
        super(context, builder);
    }


    @Override
    public final void onNext(T t) {
        super.onNext(t);
    }

    private void autoThrowable(Throwable throwable) {
        /**
         * 输出错误日志
         */
        if (throwable != null) {
            if (throwable instanceof CompositeException) {
                List<Throwable> throwables = ((CompositeException) throwable).getExceptions();
                if (throwables != null) {
                    for (Throwable throwableTemp : throwables) {
                        parseThrowable(throwableTemp);
                    }
                }
            } else {
                parseThrowable(throwable);
            }
        }
    }

    private void parseThrowable(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            try {
                String[] datas = JHttpLoggingInterceptor.getHttpLog(
                        httpException.response().raw().request(),
                        httpException.response().raw(),
                        0
                );
                log(datas);
            } catch (IOException e) {
                DLog.e(ThrowableUtil.getMessage(e));
            }
        } else {
            DLog.e(ThrowableUtil.getMessage(throwable));
        }
    }

    void log(String... messages) {
        DLog.e(messages);
    }

    @Override
    public void onError(Throwable throwable) {

        autoThrowable(throwable);

        ApiException apiException = ExceptionEngine.handleException(throwable);

        if (isShowMessage) {
            onShowMessage(apiException);
        }
        if (isShowLoad) {
            onDissLoad();
        }
        /**
         * 升级检查
         */
        if (apiException.object != null &&
                (BaseResponse.SUCCESS_CODE_UPGRADE.equals("" + apiException.status)
                        || BaseResponse.SUCCESS_CODE_UPGRADE_FOUCE.equals("" + apiException.status))) {

            if (onUpgradeError(apiException)) {
               // TODO 更新逻辑
            }
            return;
        }

        /**
         * 其他错误
         */
        else {
            _onError(apiException);
        }
    }

    protected boolean onUpgradeError(ApiException apiException) {
        return true;
    }


    protected boolean onSSOError(ApiException apiException) {
        return true;
    }

    /**
     * 权限失效会自动登出, V1版本权限失效主要依据为 401 {@link ExceptionEngine#handleException(Throwable)}
     * 所有需要验证权限的接口，Authorization token 缺失或校验失败都将触发401 {@link }
     *
     * @param apiException
     */
    @Override
    protected void onPermissionError(ApiException apiException) {
       // TODO 退出登录
    }
}
