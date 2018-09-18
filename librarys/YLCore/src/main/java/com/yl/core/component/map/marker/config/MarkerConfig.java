package com.yl.core.component.map.marker.config;

import com.amap.api.maps.model.BitmapDescriptor;

/**
 * Created by zm on 2018/9/14.
 */

public class MarkerConfig {

    public BitmapDescriptor getBitmapDescriptor() {
        return bitmapDescriptor;
    }

    BitmapDescriptor bitmapDescriptor;

    private MarkerConfig(Builder builder) {
        if (builder != null) {
            bitmapDescriptor = builder.bitmapDescriptor;
        }
    }

    public static class Builder {

        BitmapDescriptor bitmapDescriptor;

        public Builder setBitmapDescriptor(BitmapDescriptor bitmapDescriptor) {
            this.bitmapDescriptor = bitmapDescriptor;
            return this;
        }

        public Builder() {
        }

        public MarkerConfig build() {
            return new MarkerConfig(this);
        }
    }
}
