package com.yl.core.component.map.marker.convert;

import com.yl.core.component.map.marker.config.MarkerConfig;

import java.io.IOException;

import retrofit2.Converter;

/**
 * Created by zm on 2018/9/14.
 */

public interface MarkerConvert<F, T, E extends MarkerConfig> {

    T convert(F values, E config) throws IOException;

    abstract class Factory {

        public Converter<?, ?> beansConverter() {
            return null;
        }
    }
}
