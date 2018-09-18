package com.yl.merchat.module.splash;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.merchat.base.BaseActivity;
import com.yl.merchat.component.toast.ToastUtils;
import com.yl.merchat.module.main.MainActivity;
import com.yl.merchat.R;

/**
 * 闪屏页
 * <p>
 * Created by zm on 2018/9/11
 */
public class SplashActivity extends BaseActivity<ISplashView, SplashPresenter> implements ISplashView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        jumpMain();
    }

    protected void jumpMain() {
        new Handler().postDelayed(() -> {
            new RxPermissions(this)
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .subscribe( granted -> {
                        if (granted) {
                            startActivity(SplashActivity.this, MainActivity.class);
                            finish();
                        }else {
                            ToastUtils.shortToast(getString(R.string.permissions_phone_state));
                        }
                    });
        }, 2000);
    }
}
