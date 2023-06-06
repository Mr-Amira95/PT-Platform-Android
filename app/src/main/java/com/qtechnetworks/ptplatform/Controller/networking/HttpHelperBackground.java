package com.qtechnetworks.ptplatform.Controller.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;


public class HttpHelperBackground {

    private CallBack callback;



    public void Post(Context context,final String url, final int tag, final Class clazz, final Map<String, Object> params) {


        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.post(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {


                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        try {

                            result(clazz, ((HttpException) e).response().errorBody().source().readUtf8().toString(), tag, false);
                            //Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3].replace("}",""),Toast.LENGTH_LONG).show();
                        }catch (Exception g){
                            g.printStackTrace();
                        }

                        if (e instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                            try {
                                Toast.makeText(context,"Connection Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }
                        }
                        else if (e instanceof IOException)
                        {
                            // "Timeout";
                            try {
                                Toast.makeText(context,"Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }

                        }
                        else
                        {

                        }
                        callback.onError(e);
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {


                        callback.onComplete();

                    }
                } );
    }

    public void get(Context context,String url, final int tag, final Class clazz, HashMap<String, Object> map) {


        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.get(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        try {

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        try {

                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3],Toast.LENGTH_LONG).show();
                        }catch (Exception g){
                            g.printStackTrace();
                        }

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
                        try {

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        callback.onComplete();

                    }
                } );

    }

    public void delete(Context context,String url, final int tag, final Class clazz) {




        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.delete(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {


                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3],Toast.LENGTH_LONG).show();
                        }catch (Exception g){
                            g.printStackTrace();
                        }

                        if (e instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                            try {
                                Toast.makeText(context,"Connection Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }
                        }
                        else if (e instanceof IOException)
                        {
                            // "Timeout";
                            try {
                                Toast.makeText(context,"Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }

                        }

                        callback.onError(e);
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {


                        callback.onComplete();

                    }
                } );

    }

    public void put(Context context,String url, final int tag, final Class clazz,HashMap<String, Object> map) {



        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.put(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {


                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3],Toast.LENGTH_LONG).show();
                        }catch (Exception g){
                            g.printStackTrace();
                        }

                        if (e instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                            try {
                                Toast.makeText(context,"Connection Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }
                        }
                        else if (e instanceof IOException)
                        {
                            // "Timeout";
                            try {
                                Toast.makeText(context,"Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }

                        }
                        else
                        {

                        }
                        callback.onError(e);
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                        callback.onComplete();

                    }
                } );

    }

    public void PostFile(Context context, final String url, final int tag, final Class clazz, MultipartBody.Part file) {




        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.uploadfile(url, file )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                        callback.onSubscribe(d);

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {

                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {


                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3],Toast.LENGTH_LONG).show();
                        }catch (Exception g){
                            g.printStackTrace();
                        }

                        if (e instanceof SocketTimeoutException)
                        {
                            // "Connection Timeout";
                            try {
                                Toast.makeText(context,"Connection Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }
                        }
                        else if (e instanceof IOException)
                        {
                            // "Timeout";
                            try {
                                Toast.makeText(context,"Timeout",Toast.LENGTH_LONG).show();
                            }catch (Exception g){
                                g.printStackTrace();
                            }

                        }
                        else
                        {

                        }
                        callback.onError(e);
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

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

