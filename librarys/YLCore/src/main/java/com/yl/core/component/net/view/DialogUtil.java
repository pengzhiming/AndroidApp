package com.yl.core.component.net.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.yl.core.R;


/**
 * Created by zm on 2018/9/9.
 */

public class DialogUtil {

    public static DialogDismissListener createLoadingDialog(final Context context, DialogCancelListener dialogCancelListener, String tips) {
        if(context == null) {
            return null;
        }
        if(context instanceof Activity && ((Activity)context).isFinishing()) {
            return null;
        }
        final CustomLoadingDialog customLoadingDialog = new CustomLoadingDialog(context, tips);
        customLoadingDialog.setDialogCancelListener(dialogCancelListener);
        customLoadingDialog.setCanceledOnTouchOutside(false);
        customLoadingDialog.setCancelable(true);
        customLoadingDialog.show();
        return new DialogDismissListener() {
            @Override
            public void onDismiss() {
                if(context == null || (context instanceof Activity && ((Activity)context).isFinishing())) {
                    return;
                }
                customLoadingDialog.dismiss();
            }
        };
    }

    public static class CustomLoadingDialog extends AlertDialog {

        private Context context;
        private String tips;
        private DialogCancelListener dialogCancelListener;
        private AnimationDrawable animationDrawable;

        public CustomLoadingDialog(Context context) {
            super(context, R.style.TransparentDialogStyle);
            this.context = context;
        }

        public CustomLoadingDialog(Context context, String tips) {
            super(context, R.style.TransparentDialogStyle);
            this.context = context;
            this.tips = tips;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_custom_layout, null);

            if (TextUtils.isEmpty(tips) == false) {
                TextView tvTips = (TextView) view.findViewById(R.id.dialog_loading_custom_tv_tips);
                tvTips.setVisibility(View.VISIBLE);
                tvTips.setText(tips);
            }

            Window dialogWindow = getWindow();
            dialogWindow.setContentView(view);
            this.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(animationDrawable != null) {
                        animationDrawable.stop();
                    }
                    if (dialogCancelListener != null) {
                        dialogCancelListener.onCancel();
                    }
                }
            });
        }

        @Override
        public void onWindowFocusChanged(boolean hasFocus) {
            super.onWindowFocusChanged(hasFocus);
            if(animationDrawable != null) {
                animationDrawable.start();
            }
        }

        public DialogCancelListener getDialogCancelListener() {
            return dialogCancelListener;
        }

        public void setDialogCancelListener(DialogCancelListener dialogCancelListener) {
            this.dialogCancelListener = dialogCancelListener;
        }
    }

    public interface DialogDismissListener {
        void onDismiss();
    }

    public interface DialogCancelListener {
        void onCancel();
    }
}
