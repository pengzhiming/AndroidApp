package com.yl.core.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘管理
 *
 * Created by zm on 2018/9/9.
 */

public class KeyboardUtil {

    private KeyboardUtil() {
    }

    public static void openSoftKeyboard(EditText et) {
        if (et != null) {
            et.setFocusable(true);
            et.setFocusableInTouchMode(true);
            et.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et.getContext().getSystemService("input_method");
            inputManager.showSoftInput(et, 0);
        }

    }

    public static void closeSoftKeyboard(Context context) {
        if (context != null && context instanceof Activity && ((Activity) context).getCurrentFocus() != null) {
            try {
                View e = ((Activity) context).getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) e.getContext().getSystemService("input_method");
                e.clearFocus();
                imm.hideSoftInputFromWindow(e.getWindowToken(), 0);
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }
    }

    public static void closeSoftKeyboard(View view) {
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
