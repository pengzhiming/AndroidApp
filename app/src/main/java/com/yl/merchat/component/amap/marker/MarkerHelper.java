package com.yl.merchat.component.amap.marker;

import com.yl.core.component.map.marker.config.MarkerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2018/9.14.
 */

public class MarkerHelper {

    public enum MarkerType {

        /**
         * 默认类型
         */
        _DEFAULT,
        /**
         * 起点和终点
         */
        START,
        /**
         * 起点和终点
         */
        END,
        /**
         * 我的位置
         */
        ME;

        /**
         * 图层
         * @return
         */
        public int zIndex() {
            switch (this) {
                case ME:
                    return 1;
                default:
                    break;
            }
            return 0;
        }
    }

    /**
     * 获取起点终点的marker
     * @param start
     * @param end
     * @return
     */
    public static MarkerBuilder getStartAndEndMarkerBuilder(CarMarkerConvertFactory.BaseMarkerOption baseMarkerOption, boolean isStart, MarkerType type) {
        List<CarMarkerConvertFactory.BaseMarkerOption> options = new ArrayList<>();
        options.add(baseMarkerOption);
        MarkerConfig config = new MarkerConfig.Builder()
                .setBitmapDescriptor(CarMarkerIconHelper.getStartEndBitmapDescriptor(isStart))
                .build();
        return new MarkerBuilder.Builder()
                .setDatas(CarMarkerConvertFactory.create().baseMarkerOptionToConverter(options, config))
                .setType(type)
                .build();
    }
}
