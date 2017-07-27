package com.lz.module.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lz.BuildConfig;
import com.lz.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ((TextView)findViewById(R.id.tv_content)).setText(BuildConfig.API_HOST);
    }

}
