package com.yl.core.component.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.PresenterMvpFactory;
import com.yl.core.component.mvp.factory.PresenterMvpFactoryImpl;
import com.yl.core.component.mvp.presenter.BaseMvpPresenter;
import com.yl.core.component.mvp.proxy.BaseMvpProxy;
import com.yl.core.component.mvp.proxy.PresenterProxyInterface;

public class AbstractMvpAppCompatActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends AppCompatActivity implements PresenterProxyInterface<V,P> {
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,P>createFactory(getClass()));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.v("perfect-mvp","V onCreate");
        DLog.v("perfect-mvp","V onCreate mProxy = " + mProxy);
        DLog.v("perfect-mvp","V onCreate this = " + this.hashCode());
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DLog.v("perfect-mvp","V onResume");
        mProxy.onResume((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DLog.v("perfect-mvp","V onDestroy = " + isChangingConfigurations());
        mProxy.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        DLog.v("perfect-mvp","V onSaveInstanceState");
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }

    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        DLog.v("perfect-mvp","V setPresenterFactory");
        mProxy.setPresenterFactory(presenterFactory);
    }

    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        DLog.v("perfect-mvp","V getPresenterFactory");
        return mProxy.getPresenterFactory();
    }

    @Override
    public P getMvpPresenter() {
        DLog.v("perfect-mvp","V getMvpPresenter");
        return mProxy.getMvpPresenter();
    }
}
