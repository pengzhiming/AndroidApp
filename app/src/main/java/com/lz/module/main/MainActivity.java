package com.lz.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lz.BuildConfig;
import com.lz.R;
import com.lz.module.practice.draw.DrawActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.tv_content)).setText(BuildConfig.API_HOST);
        findViewById(R.id.btn_draw).setOnClickListener(this);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_draw:
                DrawActivity.actionStart(this);
                break;
        }
    }
}
