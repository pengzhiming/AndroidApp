package com.yl.merchat.module.common.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.yl.merchat.widget.web.DWebView;
import com.yl.merchat.R;

/**
 * Created by zm on 2018/9/10.
 */

public class WebActivity extends AppCompatActivity {

    public final static String EXTRA_TITLE = "EXTRA_TITLE";
    public final static String EXTRA_URL = "EXTRA_URL";
    public final static String EXTRA_ISWITHCACHE = "EXTRA_ISWITHCACHE";
    public final static String EXTRA_ISSHARE = "EXTRA_ISSHARE";

    private ProgressBar progressBar;
    private DWebView wvContent;

    public static void startActivity(Context context, String url, String title) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url, String title, boolean isCache) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_ISWITHCACHE, isCache);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, String url, String title, boolean isCache, boolean isShare) {
        Intent intent = new Intent();
        intent.setClass(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, url);
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_ISWITHCACHE, isCache);
        intent.putExtra(EXTRA_ISSHARE, isShare);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        progressBar = findViewById(R.id.progress_bar);
        wvContent = findViewById(R.id.wv_content);

        Intent intent = getIntent();
        if (intent == null) return;
        // TODO 标题
        String title = intent.getStringExtra(EXTRA_TITLE);
        // init webView
        String url = intent.getStringExtra(EXTRA_URL);
        initWebView(url == null ? "" : url);

    }

    protected void initWebView(final String url) {
        progressBar.setMax(100 * 10000);
        wvContent.setOnJWebViewProgressLinstener(new DWebView.OnJWebViewProgressLinstener() {
            @Override
            public void showLoading(int newProgress) {
                WebActivity.this.showLoading(newProgress);
            }

            @Override
            public void cancelShowLoading() {
                WebActivity.this.cancelShowLoading();
            }
        });
        wvContent.loadUrl(url);
    }

    protected void showLoading(int newProgress) {
        progressBar.setProgress(newProgress * 10000);
        if (newProgress == 100) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }

    protected void cancelShowLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wvContent.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        wvContent.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wvContent.canGoBack()) {
                wvContent.goBack();
                return true;
            }
            wvContent.loadData("", "text/html; charset=UTF-8", null);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}