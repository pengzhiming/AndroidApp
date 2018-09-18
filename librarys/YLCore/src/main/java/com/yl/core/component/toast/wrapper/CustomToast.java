package com.yl.core.component.toast.wrapper;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.core.R;


/**
 * Created by zm on 2018/9/8.
 */

public class CustomToast implements IToast {


    public static void showShort(Context context, CharSequence text) {
        makeText(context, text, LENGTH_SHORT).show();
    }

    public static void showLong(Context context, CharSequence text) {
        makeText(context, text, LENGTH_LONG).show();
    }

    public static IToast makeText(Context context, CharSequence text, int duration) {
        CustomToast result = new CustomToast(context);
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.transient_notification, null);
        TextView tv = (TextView) v.findViewById(R.id.message);
        tv.setText(text);
        result.mNextView = v;
        result.mDuration = duration;
        return result;
    }

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    final Context mContext;
    final GN mGN;
    int mDuration;
    View mNextView;

    public CustomToast(Context context) {
        mContext = context;
        mGN = new GN();
        mGN.mY = context.getResources().getDimensionPixelSize(
                R.dimen.toast_y_offset);
        mGN.mGravity = context.getResources().getInteger(
                R.integer.config_toastDefaultGravity);
    }

    @Override
    public IToast setGravity(int gravity, int xOffset, int yOffset) {
        mGN.mGravity = gravity;
        mGN.mX = xOffset;
        mGN.mY = yOffset;
        return this;
    }

    @Override
    public IToast setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    @Override
    public IToast setView(View view) {
        mNextView = view;
        return this;
    }

    @Override
    public IToast setMargin(float horizontalMargin, float verticalMargin) {
        mGN.mHorizontalMargin = horizontalMargin;
        mGN.mVerticalMargin = verticalMargin;
        return this;
    }

    @Override
    public IToast setText(String text) {
        if (mNextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        TextView tv = (TextView) mNextView.findViewById(R.id.message);
        if (tv == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        tv.setText(text);
        return this;
    }

    @Override
    public void show() {
        if (mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }
        GN gn = mGN;
        gn.mNextView = mNextView;
        gn.show(mDuration);
    }

    @Override
    public void cancel() {
        if (null != mGN)
            mGN.hide();
    }

    private static class GN {
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        final Handler mHandler = new Handler();
        int mGravity;
        int mX, mY;
        float mHorizontalMargin;
        float mVerticalMargin;
        View mView;
        View mNextView;
        WindowManager mWM;

        final Runnable mShow = new Runnable() {
            @Override
            public void run() {
                handleShow();
            }
        };

        final Runnable mHide = new Runnable() {
            @Override
            public void run() {
                handleHide();
                mNextView = null;
            }
        };

        GN() {
            final WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = android.R.style.Animation_Toast;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        }

        void show(long duration) {
            mHandler.post(mShow);
            long delay = duration == Toast.LENGTH_LONG ? 3500 : 2000;
            mHandler.postDelayed(mHide, delay);
        }

        void hide() {
            mHandler.post(mHide);
        }

        void handleShow() {
            if (mView != mNextView) {
                handleHide();
                mView = mNextView;
                Context context = mView.getContext().getApplicationContext();
                if (context == null) {
                    context = mView.getContext();
                }
                mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    final Configuration config = mView.getContext().getResources().getConfiguration();
                    mParams.gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
                }
                final int gravity = mParams.gravity;
                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                    mParams.horizontalWeight = 1.0f;
                }
                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                    mParams.verticalWeight = 1.0f;
                }
                mParams.x = mX;
                mParams.y = mY;
                mParams.verticalMargin = mVerticalMargin;
                mParams.horizontalMargin = mHorizontalMargin;
                if (mView.getParent() != null) {
                    mWM.removeView(mView);
                }
                mWM.addView(mView, mParams);
            }
        }

        void handleHide() {
            if (mView != null) {
                if (mView.getParent() != null) {
                    mWM.removeView(mView);
                }
                mView = null;
            }
        }
    }
}
