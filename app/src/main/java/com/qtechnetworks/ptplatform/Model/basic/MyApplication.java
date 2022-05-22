package com.qtechnetworks.ptplatform.Model.basic;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.qtechnetworks.ptplatform.BuildConfig;
import com.qtechnetworks.ptplatform.Controller.networking.HttpHelper;
import com.qtechnetworks.ptplatform.Controller.networking.RetrofitServices;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication instance;
    HttpHelper httpHelper;

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    private RetrofitServices httpMethods;
    private Gson gson;
    private SharedPreferences preferences;


    public synchronized RetrofitServices getHttpMethods() {
        if (httpMethods == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .validateEagerly(true)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .client(createClient())
                    .build();

            httpMethods = retrofit.create(RetrofitServices.class);
        }
        return httpMethods;
    }


    public OkHttpClient createClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                                "Bearer "+ PreferencesUtils.getUserToken()).header("Accept-Language",PreferencesUtils.getUserlanguage());


                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }).readTimeout(1, TimeUnit.MINUTES).
                connectTimeout(1, TimeUnit.MINUTES).build();


        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        Realm.init(this);
//        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
//                .name("tasky.realm")
//                .schemaVersion(0)
//                .build();
//        Realm.setDefaultConfiguration(realmConfig);

        instance = this;
    }

    public synchronized HttpHelper getHttpHelper() {
        if (httpHelper == null)
            httpHelper = new HttpHelper();
        return httpHelper;
    }

    public synchronized SharedPreferences getPreferences() {
        if (preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences;
    }

    public synchronized Gson getGson() {
        if (gson == null)
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
        return gson;
    }
}
