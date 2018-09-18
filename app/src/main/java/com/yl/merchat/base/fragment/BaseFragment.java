package com.yl.merchat.base.fragment;

import android.view.View;

import com.yl.core.component.mvp.presenter.BaseMvpPresenter;
import com.yl.core.component.mvp.view.AbstractFragment;
import com.yl.core.component.mvp.view.BaseMvpView;

/**
 * Created by zm on 2018/9/14.
 */

public class BaseFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends AbstractFragment<V, P> {

    protected View rootView;
}
