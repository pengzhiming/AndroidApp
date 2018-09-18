package com.yl.merchat.component.amap.marker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.yl.merchat.YLApplication;
import com.yl.merchat.R;

import java.lang.ref.SoftReference;

/**
 * Created by zm on 2018/9.14.
 */

public class CarMarkerIconHelper {

    static SoftReference<BitmapDescriptor> bitmapDescriptor;
    static SoftReference<BitmapDescriptor> bitmapDescriptorDefault;

    public static BitmapDescriptor getCarBitmapDescriptor(Bitmap bitmap) {

        /**
         * 存在活动的icon 使用活动的icon
         */
        if (bitmapDescriptor != null && bitmapDescriptor.get() != null) {
            return bitmapDescriptor.get();
        }
        /**
         * 存在新的活动的icon 使用新活动的icon
         */
        else if (bitmap != null) {
            bitmapDescriptor = new SoftReference<>(BitmapDescriptorFactory.fromBitmap(bitmap));
            return bitmapDescriptor.get();
        }

        if (bitmapDescriptorDefault != null && bitmapDescriptorDefault.get() != null) {
            return bitmapDescriptorDefault.get();
        } else {
            bitmapDescriptorDefault = new SoftReference<>(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(YLApplication.getContext().getResources(), R.drawable.icon_point_start)));
            return bitmapDescriptorDefault.get();
        }
    }

    public static BitmapDescriptor getStartEndBitmapDescriptor(boolean isStart) {
        return BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(YLApplication.getContext().getResources(),
                isStart
                        ? R.drawable.icon_point_start
                        : R.drawable.icon_point_end
        ));
    }


}
