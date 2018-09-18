package com.yl.merchat.component.amap.marker;

import com.yl.core.component.map.marker.YLMarkerOptions;

import java.util.List;

/**
 * Created by zm on 2018/9.14.
 */

public class MarkerBuilder {

    public List<YLMarkerOptions> getDatas() {
        return datas;
    }

    public MarkerHelper.MarkerType getType() {
        return type == null ? MarkerHelper.MarkerType._DEFAULT : type;
    }

    List<YLMarkerOptions> datas;
    MarkerHelper.MarkerType type;

    private MarkerBuilder(Builder builder) {
        if (builder != null) {
            datas = builder.datas;
            type = builder.type;
        }
    }

    public static class Builder {

        MarkerHelper.MarkerType type;
        List<YLMarkerOptions> datas;

        public Builder setType(MarkerHelper.MarkerType type) {
            this.type = type;
            return this;
        }

        public Builder setDatas(List<YLMarkerOptions> datas) {
            this.datas = datas;
            return this;
        }

        public Builder() {
        }

        public MarkerBuilder build() {
            return new MarkerBuilder(this);
        }
    }
}
