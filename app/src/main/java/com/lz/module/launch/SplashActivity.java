package com.lz.module.launch;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.lz.R;
import com.lz.module.main.MainActivity;

/**
 *
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 延迟两秒启动首页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.startActivity(SplashActivity.this);
            }
        },2000);
    }

}
