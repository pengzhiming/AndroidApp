package com.core.component.image;

import android.graphics.Bitmap;

/**
 * Created by zm on 2017/12/22.
 */

public interface LoaderListener {

    void onSuccess(Bitmap bitmap);

    void onError();
}
