package com.yl.merchat.component.toast;

import android.widget.Toast;

import com.yl.merchat.YLApplication;
import com.yl.core.component.toast.ToastUtil;

/**
 * 公共Toast入口
 * 在当前工程中请不要直接使用 系统的 Toast 进行toast弹窗 所有toast请使用当前ToastUtil控制器进行toast弹窗
 * <p>
 * Created by zm on 2017/9/8.
 */
public class ToastUtils {

    public static void shortToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int duration) {
        ToastUtil.showToast(YLApplication.getContext(), msg, duration);
    }
}
