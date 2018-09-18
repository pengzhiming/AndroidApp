package com.yl.core.component.mvp.factory;


import com.yl.core.component.mvp.presenter.BaseMvpPresenter;
import com.yl.core.component.mvp.view.BaseMvpView;

/**
 *  Presenter工厂接口
 */
public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
