package com.yl.merchat.widget.web;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yl.merchat.util.FilePathUtil;

/**
 * Created by zm on 2018/9/10.
 */

public class DWebView<T> extends WebView {

    T data;

    public DWebView(Context context) {
        this(context, null);
    }

    public DWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        String cacheDirPath = FilePathUtil.getCacheWeb();
        this.getSettings().setAppCachePath(cacheDirPath);

        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setDatabaseEnabled(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setAllowFileAccess(true);

//        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setLoadWithOverviewMode(true);

        this.setWebViewClient(new JWebViewClient());
        this.setWebChromeClient(new JWebChromeClient());
        fixWebView();
    }

    @TargetApi(11)
    private void fixWebView() {
        if (Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }

    class JWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (onJWebViewProgressLinstener != null) {
                onJWebViewProgressLinstener.cancelShowLoading();
            }
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (onJWebViewOverrideUrlLoadingLinstener != null) {
                OverrideUrlLoadingState overrideUrlLoadingState = onJWebViewOverrideUrlLoadingLinstener.shouldOverrideUrlLoading(view, url);
                if(overrideUrlLoadingState != OverrideUrlLoadingState.Default) {
                    return overrideUrlLoadingState == OverrideUrlLoadingState.False ? false : true;
                }
            }
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    class JWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                if (onJWebViewProgressLinstener != null) {
                    onJWebViewProgressLinstener.cancelShowLoading();
                }
            } else {
                if (onJWebViewProgressLinstener != null) {
                    onJWebViewProgressLinstener.showLoading(newProgress);
                }
            }

            /**
             * 设置Javascript的异常监控
             *
             * @param webView 指定被监控的webView
             * @param autoInject 是否自动注入Bugly.js文件
             * @return true 设置成功；false 设置失败
             */
            super.onProgressChanged(view, newProgress);
        }
    }

    public enum OverrideUrlLoadingState {
        Default, False, Ture
    }

    OnJWebViewOverrideUrlLoadingLinstener onJWebViewOverrideUrlLoadingLinstener;

    public void setOnJWebViewOverrideUrlLoadingLinstener(OnJWebViewOverrideUrlLoadingLinstener onJWebViewOverrideUrlLoadingLinstener) {
        this.onJWebViewOverrideUrlLoadingLinstener = onJWebViewOverrideUrlLoadingLinstener;
    }

    public interface OnJWebViewOverrideUrlLoadingLinstener {
        OverrideUrlLoadingState shouldOverrideUrlLoading(WebView view, String url);
    }

    OnJWebViewProgressLinstener onJWebViewProgressLinstener;

    public void setOnJWebViewProgressLinstener(OnJWebViewProgressLinstener onJWebViewProgressLinstener) {
        this.onJWebViewProgressLinstener = onJWebViewProgressLinstener;
    }

    public interface OnJWebViewProgressLinstener {

        void showLoading(int newProgress);

        void cancelShowLoading();
    }
}
