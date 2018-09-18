package com.yl.core.component.mvp.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.view.BaseMvpView;

/**
 * 所有Presenter的基类，并不强制实现这些方法，有需要在重写
 */
public class BaseMvpPresenter<V extends BaseMvpView> {

    /**
     * V层view
     */
    private V mView;

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePersenter(@Nullable Bundle savedState) {
        DLog.v("perfect-mvp","P onCreatePersenter = ");
    }


    /**
     * 绑定View
     */
    public void onAttachMvpView(V mvpView) {
        mView = mvpView;
        DLog.v("perfect-mvp","P onResume");
    }

    /**
     * 解除绑定View
     */
    public void onDetachMvpView() {
        mView = null;
        DLog.v("perfect-mvp","P onDetachMvpView = ");
    }

    /**
     * Presenter被销毁时调用
     */
    public void onDestroyPersenter() {
        DLog.v("perfect-mvp","P onDestroy = ");
    }

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        DLog.v("perfect-mvp","P onSaveInstanceState = ");
    }

    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    public V getMvpView() {
        return mView;
    }
}
