package com.yl.merchat.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.yl.core.component.mvp.presenter.BaseMvpPresenter;
import com.yl.core.component.mvp.view.AbstractMvpActivitiy;
import com.yl.core.component.mvp.view.AbstractMvpAppCompatActivity;
import com.yl.core.component.mvp.view.BaseMvpView;

/**
 * Created by zm on 2018/9/10.
 */
public class BaseActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends AbstractMvpAppCompatActivity<V, P> {

    /**
     * start Activity
     * @param context
     * @param targetClass
     */
    public static void startActivity(Context context, Class targetClass) {
        Intent intent = new Intent();
        intent.setClass(context, targetClass);
        if (context instanceof Activity) {
            //do nothing
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}
