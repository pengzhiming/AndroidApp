package com.yl.core.component.toast;

import android.content.Context;
import android.text.TextUtils;

import com.yl.core.component.toast.wrapper.IToast;
import com.yl.core.component.toast.wrapper.ToastCompat;


/**
 * 公共Toast入口
 * 在当前工程中请不要直接使用 系统的 Toast 进行toast弹窗 所有toast请使用当前ToastUtil控制器进行toast弹窗
 * <p>
 * Created by zm on 2017/9/8.
 */

public class ToastUtil {

    private static IToast mToast;

    private ToastUtil() {
    }

    /**
     * 使用Application的Toast
     * 建议全局使用该方法弹窗Toast
     * @param context
     * @param msg
     * @param duration
     */
    public static void showToast(Context context, String msg, int duration) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = ToastCompat.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}
