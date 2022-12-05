package com.qtechnetworks.ptplatform.Controller.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.R;
import com.qtechnetworks.ptplatform.View.Activity.MainActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpHelper {

    private CallBack callback;

    ProgressDialog dialog;

    public void Post(Context context, final String url, final int tag, final Class clazz, final Map<String, Object> params) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

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

                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3].replace("\\}}","").replace(")",""),Toast.LENGTH_LONG).show();
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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );
    }

    public void PostRaw(Context context,final String url, final int tag, final Class clazz, final Map<String, Object> params) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.postRaw(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

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

                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[4].replace("\\}}","").replace(")",""),Toast.LENGTH_LONG).show();
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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );
    }
    public void get(Context context,String url, final int tag, final Class clazz, HashMap<String, Object> map) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.get(url, map)
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
                            dialog.dismiss();
                            result(clazz, responseBody.source().readUtf8().toString(), tag, true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                        try {
                            Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[4],Toast.LENGTH_LONG).show();
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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );

    }

    public void delete(Context context,String url, final int tag, final Class clazz) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();


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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );

    }

    public void put(Context context,String url, final int tag, final Class clazz,HashMap<String, Object> map) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();


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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );

    }

    public void PostFile(Context context, final String url, final int tag, final Class clazz, MultipartBody.Part file) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.uploadfile(url, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

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

                        dialog.dismiss();

                        callback.onComplete();

                    }
                } );
    }

    public void postLogin(Context context, final String url, final int tag, final Class clazz, final JsonObject params) {

        dialog=new ProgressDialog(context);
        dialog.setMessage(context.getResources().getString(R.string.loading));
        dialog.setCancelable(false);
        dialog.show();

        RetrofitServices service = MyApplication.getInstance().getHttpMethods(context);

        service.postLogin(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

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

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[4].split("\"")[1],Toast.LENGTH_LONG).show();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });

           //                 Toast.makeText(context,((HttpException) e).response().errorBody().source().readUtf8().toString().split(":")[3],Toast.LENGTH_LONG).show();


                        if (e instanceof SocketTimeoutException) {
                            // "Connection Timeout";
                            try {
                                Toast.makeText(context,"Connection Timeout",Toast.LENGTH_LONG).show();
                            } catch (Exception g) {
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
            Log.d("Result API "+tag, str);
            callback.onNext(tag, isSuccess, MyApplication.getInstance().getGson().fromJson(str, clazz));
        }
    }

}



