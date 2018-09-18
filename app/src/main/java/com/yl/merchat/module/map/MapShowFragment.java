package com.yl.merchat.module.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amap.api.maps.MapView;
import com.yl.merchat.base.fragment.BaseMapFragment;
import com.yl.merchat.R;
import com.yl.merchat.base.fragment.BaseSensorMapFragment;

/**
 * Created by zm on 2018/9/14.
 */
public class MapShowFragment extends BaseSensorMapFragment{

    private MapView mapView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_show, container, false);
        initMap(rootView, savedInstanceState);
        return rootView;
    }

    private void initMap(View rootView, Bundle savedInstanceState) {
        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        initMap(mapView);

        Button btnLocation = rootView.findViewById(R.id.btn_location);
        btnLocation.setOnClickListener(view -> {
            moveToLastLocation();
        });
    }
}
