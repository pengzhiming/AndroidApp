
package com.yl.merchat.component.amap;


import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.yl.core.component.log.DLog;

public class LocationPresenter implements AMapLocationListener {

    /**
     * 定位相关
     */
    private AMapLocationClient mapLocationClient;
    private AMapLocationClientOption mLocationOption;

    private Context context;
    private boolean isInterceptLocationResult = true;


    /**
     * 最后一次获取到的定位经纬度
     */
    private LatLng lastLatLng;

    public LocationPresenter(Context context) {
        this.context = context;
    }

    public LocationPresenter(Context context, boolean isInterceptLocationResult) {
        this.context = context;
        this.isInterceptLocationResult = isInterceptLocationResult;
    }

    public void setInterceptLocationResult(boolean isInterceptLocationResult) {
        this.isInterceptLocationResult = isInterceptLocationResult;
    }

    private void resetOption(boolean isOnce) {
        if (mLocationOption == null) {
            mLocationOption = new AMapLocationClientOption();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mLocationOption.setSensorEnable(true);
        mLocationOption.setGpsFirst(false);
        mLocationOption.setOnceLocation(isOnce);
    }

    public void startLocation() {
        startLocation(false);
    }

    public void startLocation(boolean isOnce) {
        resetOption(isOnce);
        if (mapLocationClient == null) {
            mapLocationClient = new AMapLocationClient(context.getApplicationContext());
            mapLocationClient.setLocationListener(this);
        }
        mapLocationClient.setLocationOption(mLocationOption);
        mapLocationClient.startLocation();
    }

    public void stopLocation() {
        if (mapLocationClient != null) {
            mapLocationClient.stopLocation();
        }
    }

    public void destroyLocation() {
        if (null != mapLocationClient) {
            mapLocationClient.onDestroy();
            mapLocationClient = null;
            mLocationOption = null;
        }
    }

    public LatLng getLastLatLng() {
        if (mapLocationClient != null) {
            AMapLocation location = mapLocationClient.getLastKnownLocation();
            if (location != null) {
                lastLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }
        return lastLatLng;
    }

    static Long lastTime;
    private boolean isShow() {
        boolean result = false;
        long current = System.currentTimeMillis();
        if(lastTime == null) {
            lastTime = new Long(0);
        }
        int second = (int) ((current - lastTime) / 1000);
        if(second > 60) {
            result = true;
            lastTime = current;
        }
        return result;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (locationListener != null) {
            if (isInterceptLocationResult) {
                int errorCode = aMapLocation.getErrorCode();

                /**
                 * 定位权限
                 * @see http://lbs.amap.com/faq/android/android-location/332/
                 * <p>如果SDK明确检查出定位权限缺失时会返回错误码12，如果没有检查出来定位权限缺失，但此时所有定位数据源又都不可用，则会返回13。无论返回的是12或者是13，都说明设备现在所处环境不足以完成定位功能。</p>
                 */
                if (errorCode == 12 || errorCode == 13) {
                    if(isShow()) {
                        DLog.e("去开启权限");
                    }
                    return;
                }
            }
            locationListener.onLocationChanged(aMapLocation);
        }
    }

    public void setMapLocationListener(AMapLocationListener locationListener) {
        this.locationListener = locationListener;
    }

    AMapLocationListener locationListener;
}
