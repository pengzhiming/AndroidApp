package com.yl.merchat.base.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yl.core.component.log.DLog;
import com.yl.core.component.map.marker.YLMarkerOptions;
import com.yl.merchat.component.amap.LocationPresenter;
import com.yl.merchat.component.amap.marker.MarkerBuilder;
import com.yl.merchat.component.amap.marker.MarkerHelper;
import com.yl.merchat.R;
import com.yl.merchat.component.toast.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zm on 2018/9/14.
 */

public class BaseMapFragment extends Fragment implements AMap.OnMarkerClickListener, AMapLocationListener {

    /**
     * 默认的地图缩放比例
     */
    protected final int MAP_ZOOM_SIZE_NORMAL = 17;

    protected MapView mapView;
    protected AMap aMap;

    /**
     * 定位相关
     */
    protected LocationPresenter locationPresenter;

    /**
     * 固定的定位marker
     */
    protected Marker locMarker;
    protected BitmapDescriptor pointerBitmapDescriptor;
    protected MarkerOptions pointerOptions;

    /**
     * 旋转的罗盘定位marker
     */
    protected Marker pointerMarker;
    protected BitmapDescriptor compassBitmapDescriptor;
    protected MarkerOptions compassOptions;

    /**
     * 自动管理定位，后台或者关闭时停止定位
     */
    protected boolean isAutoManageLocation = true;

    public LatLng getLastLatLng() {
        return locationPresenter == null ? null : locationPresenter.getLastLatLng();
    }

    /**
     * 存在地图上的markers
     */
    private HashMap<MarkerHelper.MarkerType, List<Marker>> markersMap = new HashMap<>();

    protected int mapZoom = MAP_ZOOM_SIZE_NORMAL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AMap getaMap() {
        return aMap;
    }

    protected void initMap(MapView mapView) {
        this.mapView = mapView;
        initMap(mapView.getMap());
    }

    @SuppressLint("CheckResult")
    protected void initMap(AMap amap) {
        aMap = amap;
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        // 关闭旋转
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        // 设置倾斜手势是否可用。
        aMap.getUiSettings().setTiltGesturesEnabled(false);
        aMap.setMyLocationEnabled(true);
        aMap.showIndoorMap(true);

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        myLocationStyle.strokeColor(Color.WHITE);
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.shape_compass_transparent));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        aMap.setMyLocationStyle(myLocationStyle);

        /**
         * 地图marker点击监听 {@link #onMarkerClick}
         */
        aMap.setOnMarkerClickListener(this);
        /**
         *  地图移动摄像头监听
         */
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChangeFinish(CameraPosition arg0) {
                BaseMapFragment.this.onCameraChangeFinish(arg0);
            }

            @Override
            public void onCameraChange(CameraPosition arg0) {
                BaseMapFragment.this.onCameraChange(arg0);
            }
        });
        /**
         * 点击marker 回调 InfoWindowAdapter 控制弹出的窗口渲染
         */
        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return BaseMapFragment.this.getInfoWindow(marker);
            }

            @Override
            public View getInfoContents(Marker marker) {
                return BaseMapFragment.this.getInfoContents(marker);
            }
        });

        /**
         * 读取缓存地理位置，并调整缩放比例到默认
         */
//        LatLng arg0 = null; // TODO 获取经纬度
//        arg0 = arg0 == null ? new LatLng(0, 0) : arg0;
//        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arg0, MAP_ZOOM_SIZE_NORMAL));

        locationPresenter = new LocationPresenter(getContext().getApplicationContext());
        locationPresenter.setMapLocationListener(this);

        /**
         * 定位权限验证
         */
        new RxPermissions(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe( granted -> {
                   if (granted) {
                        locationPresenter.startLocation();
                   }else {
                       ToastUtils.shortToast(getString(R.string.permissions_location));
                   }
                });
    }

    /**
     * 地图中心点移动结束回调
     *
     * @param cameraPosition
     */
    protected void onCameraChangeFinish(CameraPosition cameraPosition) {
    }

    /**
     * 地图中心点移动时回调
     *
     * @param cameraPosition
     */
    protected void onCameraChange(CameraPosition cameraPosition) {
    }

    /**
     * 点击marker 回调 InfoWindowAdapter 控制弹出的窗口渲染
     *
     * @param marker
     */
    protected View getInfoContents(Marker marker) {
        return null;
    }


    protected View getInfoWindow(Marker marker) {
        return null;
    }

    /**
     * 点击marker
     *
     * @param marker
     * @return 返回:true 表示点击marker 后marker 不会移动到地图中心； 返回false 表示点击marker 后marker 会自动移动到地图中心
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        // 过滤掉我的位置的点击
        if (marker.getZIndex() == MarkerHelper.MarkerType.ME.zIndex()) {
            return true;
        }
        /**
         * amap 回调 {@link #setInfoWindowAdapter}
         */
        marker.showInfoWindow();
        return true;
    }

    /**
     * 添加我的定位点 包含固定marker 和 旋转罗盘marker
     *
     * @param latlng
     */
    protected void addMarker(LatLng latlng) {
        if (aMap == null) {
            return;
        }
        List<Marker> markerList;
        if (markersMap.containsKey(MarkerHelper.MarkerType.ME)) {
            markerList = markersMap.get(MarkerHelper.MarkerType.ME);
        } else {
            markerList = new ArrayList<>();
            markersMap.put(MarkerHelper.MarkerType.ME, markerList);
        }

        pointerBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_compass_pointer);
        pointerOptions = new MarkerOptions();
        pointerOptions.icon(pointerBitmapDescriptor);
        pointerOptions.anchor(0.5f, 0.5f);
        pointerOptions.position(latlng);
        pointerOptions.zIndex(MarkerHelper.MarkerType.ME.zIndex());
        pointerMarker = aMap.addMarker(pointerOptions);
        markerList.add(pointerMarker);

        compassBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_compass);
        compassOptions = new MarkerOptions();
        compassOptions.icon(compassBitmapDescriptor);
        compassOptions.anchor(0.5f, 0.5f);
        compassOptions.position(latlng);
        compassOptions.zIndex(MarkerHelper.MarkerType.ME.zIndex());
        locMarker = aMap.addMarker(compassOptions);
        markerList.add(locMarker);
    }

    /**
     * 添加marker {@link MarkerHelper}
     * <p>
     * 统一在MarkerHelper中实现该类型marker {@code addMarkers(MarkerHelper.getCarMarkerBuilder(cars, null);}
     * </p>
     *
     * @param builder
     */
    protected void addMarkers(MarkerBuilder builder) {
        if (aMap == null || builder == null || builder.getDatas() == null || builder.getDatas().isEmpty()) {
            return;
        }

        List<Marker> markerList;
        if (markersMap.containsKey(builder.getType())) {
            markerList = markersMap.get(builder.getType());
        } else {
            markerList = new ArrayList<>();
            markersMap.put(builder.getType(), markerList);
        }

        // 渲染并加入缓存
        for (YLMarkerOptions markerOption : builder.getDatas()) {
            if (markerOption.getMarkerOptions() != null) {
                Marker marker = aMap.addMarker(markerOption.getMarkerOptions());
                markerList.add(marker);
                marker.setRotateAngle(markerOption.getRotateAngle());
            }
        }
    }

    protected void showInfoWindows(MarkerHelper.MarkerType type) {
        if (type == null || !markersMap.containsKey(type)) {
            return;
        }

        List<Marker> markerList = markersMap.get(type);
        for (Marker marker : markerList) {
            marker.showInfoWindow();
        }
    }

    /**
     * 移除指定类型的markers
     *
     * @param type {@link MarkerHelper.MarkerType}
     */
    protected void removeMarkers(MarkerHelper.MarkerType type) {
        if (type == null || !markersMap.containsKey(type)) {
            return;
        }
        List<Marker> markerList = markersMap.get(type);
        if (markerList == null || markerList.isEmpty()) {
            return;
        }
        for (Marker marker : markerList) {
            marker.remove();
            // V5.0 + 版本无需再调用此方法，否则无法显示后续插入的marker
//            marker.destroy();
        }
        markerList.clear();
    }

    /**
     * 个人位置marker
     *
     * @param location
     */
    protected void locationMarkerChanged(LatLng location) {
        if (location == null) {
            return;
        }

        if (locMarker == null || pointerMarker == null) {
            if(mapView != null) {
                mapView.post(() -> aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, mapZoom)));
            } else {
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, mapZoom));
            }
            addMarker(location);
        } else {
            pointerMarker.setPosition(location);
            locMarker.setPosition(location);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null) {
            return;
        }

        if (aMapLocation.getErrorCode() == 0) {

            // V5.0 版本个人位置会自动 移动视角到中心
//                onLocationChangedListener.onLocationChanged(aMapLocation);

            // 移动个人定位点
            locationMarkerChanged(getLastLatLng());
            DLog.d("定位信息， getPoiName: " + aMapLocation.getPoiName() + " Lat: " + aMapLocation.getLatitude() + " Lot: " + aMapLocation.getLatitude() + " Speed: " + aMapLocation.getSpeed());
        }else {
            DLog.e("定位错误信息","location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }

    /**
     * 移动到最后的定位点
     */
    public void moveToLastLocation() {
        moveToLastLocation(getLastLatLng());
    }

    public void moveToLastLocation(LatLng latLng) {
        if (aMap == null || latLng == null) {
            return;
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mapZoom));
    }

    public void restartLocation() {
        if(locationPresenter != null) {
            locationPresenter.startLocation();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAutoManageLocation) {
            if(locationPresenter != null) {
                locationPresenter.startLocation();
            }
        }
        if(mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isAutoManageLocation) {
            if(locationPresenter != null) {
                locationPresenter.stopLocation();
            }
        }
        // TODO 缓存最后的位置信息
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationPresenter != null) {
            locationPresenter.destroyLocation();
        }
        if(mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
