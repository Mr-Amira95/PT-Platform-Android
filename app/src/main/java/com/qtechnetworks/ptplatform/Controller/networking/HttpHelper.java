package com.qtechnetworks.ptplatform.Controller.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.qtechnetworks.ptplatform.Model.basic.MyApplication;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class HttpHelper {

    private CallBack callback;

    ProgressDialog dialog;


    public void get(String url, final int tag, final Class clazz, HashMap<String, Object> map, Context context, String header, String language) {

        dialog=new ProgressDialog(context);
        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);


        RetrofitServices service = MyApplication.getInstance().getHttpMethods();

        service.get(url, map,"Bearer "+header,language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        dialog.show();

                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        dialog.dismiss();
                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();
                        if (e instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                        }
                        else if (e instanceof IOException)
                        {
                            // "Timeout";
                        }
                        else
                        {

                        }
                        callback.onError(e);
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );

    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    private void result(Class clazz, String str, int tag, boolean isSuccess) {
        if (callback != null) {
            Log.d("Result API", str);
            callback.onNext(tag, isSuccess, MyApplication.getInstance().getGson().fromJson(str, clazz));
        }
    }

}

