package com.yl.core.component.toast.wrapper;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zm on 2018/9/8.
 */

public class SystemToast implements IToast {

    private Context mContext;
    private Toast mToast;

    public static IToast makeText(Context context, String text, int duration) {
        return new SystemToast(context).setText(text).setDuration(duration);
    }

    public SystemToast(Context context) {
        mContext = context;
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
    }


    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        mToast.setGravity(gravity, xOffset, yOffset);
        return this;
    }

    @Override
    public IToast setDuration(int duration) {
        mToast.setDuration(duration);
        return this;
    }

    @Override
    public IToast setView(View view) {
        mToast.setView(view);
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mToast.setMargin(horizontalMargin, verticalMargin);
        return this;
    }

    @Override
    public IToast setText(String text) {
        mToast.setText(text);
        return this;
    }

    @Override
    public void show() {
        if (null != mToast)
            mToast.show();
    }

    @Override
    public void cancel() {
        if (null != mToast)
            mToast.cancel();
    }
}
