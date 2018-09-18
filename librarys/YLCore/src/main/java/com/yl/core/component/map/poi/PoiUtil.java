package com.yl.core.component.map.poi;


import com.amap.api.services.core.PoiItem;

import java.util.List;

/**
 * Created by zm on 2018/9/14.
 */

public class PoiUtil {

    public static PoiItem filterWithMinDistance(List<PoiItem> poiItemList) {
        if(poiItemList == null || poiItemList.isEmpty()) {
            return null;
        }
        float minDistance = poiItemList.get(0).getDistance();
        PoiItem poiItem = null;
        for(PoiItem poiItemTemp : poiItemList) {
            if(poiItemTemp.getDistance() < minDistance) {
                minDistance = poiItemTemp.getDistance();
                poiItem = poiItemTemp;
            }
        }
        return poiItem;
    }
}
