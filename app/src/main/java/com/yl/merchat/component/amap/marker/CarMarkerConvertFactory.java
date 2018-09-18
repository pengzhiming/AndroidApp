package com.yl.merchat.component.amap.marker;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.yl.core.component.map.marker.YLMarkerOptions;
import com.yl.core.component.map.marker.config.MarkerConfig;
import com.yl.core.component.map.marker.convert.MarkerConvert;
import com.yl.merchat.model.vo.bean.GpsBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Converter;

/**
 * Created by zm on 2018/9.14.
 */

public class CarMarkerConvertFactory extends MarkerConvert.Factory {

    MarkerConvert<?, ?, ?> converter;

    public static CarMarkerConvertFactory create() {
        return new CarMarkerConvertFactory();
    }

    private CarMarkerConvertFactory() {
    }

    @Override
    public Converter<?, ?> beansConverter() {
        return null;
    }

    public List<YLMarkerOptions> carGps2Converter(List<GpsBean> cars, MarkerConfig config) {
        return new CarGps2ToMarkerConverter().convert(cars, config);
    }

    public List<YLMarkerOptions> baseMarkerOptionToConverter(List<BaseMarkerOption> markerOptions, MarkerConfig config) {
        return new BaseMarkerOptionToMarkerConverter().convert(markerOptions, config);
    }

    static final class CarGps2ToMarkerConverter implements MarkerConvert<List<GpsBean>, List<YLMarkerOptions>, MarkerConfig> {

        @Override
        public List<YLMarkerOptions> convert(List<GpsBean> values, MarkerConfig config)  {
            if (values == null || values.isEmpty()) {
                return null;
            }

            ArrayList<YLMarkerOptions> markerOptionsArrayList = new ArrayList<>();
            for (GpsBean carGps2 : values) {

                YLMarkerOptions dzMarkerOptions = new YLMarkerOptions();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.zIndex(0);
                markerOptions.position(new LatLng(carGps2.getLat(), carGps2.getLon()));

                if(config != null) {
                    markerOptions.icon(config.getBitmapDescriptor());
                }

                dzMarkerOptions.setMarkerOptions(markerOptions);
                markerOptionsArrayList.add(dzMarkerOptions);
            }
            return markerOptionsArrayList;
        }
    }

    public static class BaseMarkerOption {
        LatLng latLng;
        String title;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public String getTitle() {
            return title;
        }

        public LatLng getLatLng() {
            return latLng;
        }

        public BaseMarkerOption() {

        }

        public BaseMarkerOption(LatLng latLng, String title) {
            this.latLng = latLng;
            this.title = title;
        }
    }

    static final class BaseMarkerOptionToMarkerConverter implements MarkerConvert<List<BaseMarkerOption>, List<YLMarkerOptions>, MarkerConfig> {

        @Override
        public List<YLMarkerOptions> convert(List<BaseMarkerOption> values, MarkerConfig config)  {
            if (values == null || values.isEmpty()) {
                return null;
            }

            YLMarkerOptions dzMarkerOptions = new YLMarkerOptions();
            ArrayList<YLMarkerOptions> markerOptionsArrayList = new ArrayList<>();
            for (BaseMarkerOption baseMarkerOption : values) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.setFlat(true);
                markerOptions.anchor(0.5f, 0.5f);
                markerOptions.zIndex(0);
                markerOptions.position(baseMarkerOption.getLatLng());
                markerOptions.title(baseMarkerOption.getTitle());

                if(config != null) {
                    markerOptions.icon(config.getBitmapDescriptor());
                }

                dzMarkerOptions.setMarkerOptions(markerOptions);
                markerOptionsArrayList.add(dzMarkerOptions);
            }
            return markerOptionsArrayList;
        }
    }
}
