package com.yl.core.component.map.view;

import android.content.Context;
import android.util.AttributeSet;

import com.amap.api.maps.MapView;

/**
 * Created by zm on 2018/9/14.
 */

public class YLMap extends MapView{

    Context context;

    public YLMap(Context context) {
        this(context, null);
    }

    public YLMap(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public YLMap(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.context = context;
    }

    public void init() {
    }
}
