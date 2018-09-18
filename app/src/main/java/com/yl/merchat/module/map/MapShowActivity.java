package com.yl.merchat.module.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yl.merchat.R;


public class MapShowActivity extends AppCompatActivity {

    private MapShowFragment mapShowFragment;
    Bundle savedInstanceState;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MapShowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_map_show);

        initFragment();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mapShowFragment", mapShowFragment);
    }

    private void initFragment() {
        if (savedInstanceState != null) {
            mapShowFragment = (MapShowFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mapShowFragment");
        }
        if (mapShowFragment == null) {
            mapShowFragment = new MapShowFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, mapShowFragment)
                .commit();
    }

}
