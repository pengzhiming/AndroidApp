package com.yl.core.component.map.strategy;

/**
 * Created by zm on 2018/9/14.
 */

public class LocationStrategy {

    private static final float a1 = 1f;
    private static final float a2 = 1f;


    private static final int MAX_INTERVAL = 10000;
    private static final int MIN_INTERVAL = 1000;

    /**
     * 根据速率获取 定位频率间隔
     * At= (al * Δtl + a2 * Δt2 + a3* Δt3) / (al+a2+a3)
     * Δt1、Δt2和Δt3分别为根据移动速度、移动方向、移动路线分别计出的到下次定位的时间间隔；a1、a2和a3为加权值系数
     *
     * @param speed (m/s)
     * @return 定位频率间隔 （ms） min -> 1000
     * @see https://encrypted.google.com/patents/CN101938831A?cl=zh&hl=zh-CN
     */
    public static int getInterval(float speed, float direction) {

//        int interval = (int) (a1 / speed);

        int interval = 0;

        // 近乎禁止
        if(speed < 1) {
            interval = 10000;
        }
        // 步行
        else if(speed < 1) {
            interval = 10;
        }
        // 骑行
        else if(speed < 1) {
            interval = 10;
        }
        // 驾车
        else if(speed < 1) {
            interval = 10;
        }
        interval = interval > MAX_INTERVAL ? MAX_INTERVAL : interval;
        interval = interval < MIN_INTERVAL ? MIN_INTERVAL : interval;
        return interval;
    }
}
