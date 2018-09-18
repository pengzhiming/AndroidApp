package com.yl.core.component.map.marker;

import com.amap.api.maps.model.MarkerOptions;

/**
 * Created by zm on 2018/9/14.
 */

public class YLMarkerOptions {

    MarkerOptions markerOptions;

    float rotateAngle;

    public float getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public void setMarkerOptions(MarkerOptions markerOptions) {
        this.markerOptions = markerOptions;
    }
}
