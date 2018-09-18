package com.yl.merchat.component.rx;


import com.yl.core.component.net.data.IRespose;
import com.yl.core.component.net.exception.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by zm on 2017/11/24.
 */

public class RxSchedulers extends com.yl.core.component.rx.RxSchedulers{


    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> handleResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Function<T, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(T response) throws Exception {
                        if (response instanceof IRespose) {
                            if (((IRespose) response).isSuccess()) {
                                return createData(response);
                            } else {
                                return Observable.error(new ServerException(((IRespose) response).getICode(), ((IRespose) response).getMessage(), response));
                            }
                        }
                        return createData(response);
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> oe) throws Exception {
                try {
                    oe.onNext(t);
                    oe.onComplete();
                } catch (Exception e) {
                    oe.onError(e);
                }
            }
        });
    }
}
