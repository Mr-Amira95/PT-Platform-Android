package com.qtechnetworks.ptplatform.Controller.networking;

import io.reactivex.disposables.Disposable;

public interface CallBack {

    void onSubscribe(Disposable d);

    void onNext(int tag, boolean isSuccess, Object result);

    void onError(Throwable e);

    void onComplete();

}
