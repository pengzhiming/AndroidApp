package com.yl.merchat.module.main;

import android.databinding.DataBindingUtil;
import android.media.Image;
import android.os.Bundle;
import android.view.View;

import com.yl.merchat.base.BaseActivity;
import com.yl.merchat.component.databind.ClickHandler;
import com.yl.merchat.component.toast.ToastUtils;
import com.yl.merchat.model.vo.bean.NoticeBean;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.merchat.module.map.MapShowActivity;
import com.yl.merchat.R;
import com.yl.merchat.databinding.ActivityMainBinding;

import java.util.List;

@CreatePresenter(MainPresenter.class)
public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements ClickHandler, IMainView{

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1.databingding 绑定试图
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setClick(this);

        // 2.imageload  图片加载、下载
        // ImageLoader.loadImage(imageView, "url");
        // ImageLoader.loadGif(imageView, "url");
        // ImageLoader.downloadImage(context, "url", "savePath");

        // 3.日志输出
        // DLog.i("支持任意对象，json字符串自动格式化");

        // 4.Toast
        // ToastUtils.shortToast("toast通知");

        // 5.网络请求使用参考api包下SysApi

        // 6.mvp使用参考module包main模块

        // 7.常用工具类在YLCore模块和app模块下util包 时间格式化、文件操作、键盘、对象存储等

        // 8.h5界面加载 com.yl.app.module.common.web.WebActivity

        // 9.包结构用途参考README.md
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                getMvpPresenter().showToast();
                break;
            case R.id.btn_getNotices:
                getMvpPresenter().getNotices("");
                break;
            case R.id.btn_goMap:
                MapShowActivity.actionStart(this);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void showNotices(List<NoticeBean> noticeBeans) {
        DLog.d(noticeBeans);
    }
}
