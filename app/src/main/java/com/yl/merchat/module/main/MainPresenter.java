package com.yl.merchat.module.main;

import com.yl.merchat.api.SysApi;
import com.yl.merchat.component.net.YLRxSubscriberHelper;
import com.yl.merchat.model.vo.result.GetSystemNoticeResult;
import com.yl.core.component.mvp.presenter.BaseMvpPresenter;

/**
 * Created by zm on 2018/9/10.
 */
public class MainPresenter extends BaseMvpPresenter<IMainView>{

    public void showToast() {
        getMvpView().showToast("mvp流程");
    }

    /**
     * 获取公告详情
     * @param lastId
     */
    public void getNotices(String lastId) {
        new SysApi().getNoticeList(lastId, new YLRxSubscriberHelper<GetSystemNoticeResult>() {
            @Override
            public void _onNext(GetSystemNoticeResult getSystemNoticeResult) {
                getMvpView().showNotices(getSystemNoticeResult.getData());
            }
        });
    }
}
