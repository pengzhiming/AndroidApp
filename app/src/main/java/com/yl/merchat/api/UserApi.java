package com.yl.merchat.api;

import com.yl.merchat.base.data.BaseRequestBody;
import com.yl.merchat.component.net.YLRequestManager;
import com.yl.merchat.component.net.YLRxSubscriberHelper;
import com.yl.merchat.component.rx.RxSchedulers;
import com.yl.merchat.model.vo.result.GetUserInfoResult;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 用户相关api
 * <p>
 * Created by zm on 2018/9/9.
 */
public class UserApi {

    public interface Api {
        @POST("")
        Observable<GetUserInfoResult> getUserInfo(@Body RequestBody requestBody);
    }

    private Api mApi;

    public UserApi() {
        this.mApi = YLRequestManager.getRequest(Api.class);
    }

    /**
     * 获取用户信息列表
     * @param userId
     * @param rxSubscriberHelper
     */
    public void getUserInfo(String userId, YLRxSubscriberHelper<GetUserInfoResult> rxSubscriberHelper) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        mApi.getUserInfo(new BaseRequestBody(params).toRequestBody())
                .compose(RxSchedulers.<GetUserInfoResult>rxSchedulerHelper())
                .compose(RxSchedulers.<GetUserInfoResult>handleResult())
                .subscribe(rxSubscriberHelper);
    }
}
