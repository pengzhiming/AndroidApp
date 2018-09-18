package com.yl.merchat.module.main;

import com.yl.merchat.model.vo.bean.NoticeBean;
import com.yl.core.component.mvp.view.BaseMvpView;

import java.util.List;

/**
 * Created by zm on 2018/9/10.
 */
public interface IMainView extends BaseMvpView{
    void showToast(String message);
    void showNotices(List<NoticeBean> noticeBeans);
}
