package com.yl.core.component.image;

import android.graphics.Bitmap;

/**
 *  Created by zm on 2018/9/9.
 */

public interface LoaderListener {

    void onSuccess(Bitmap bitmap);

    void onError();
}
