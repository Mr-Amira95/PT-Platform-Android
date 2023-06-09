package com.company.ptplatform.Model.basic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.company.ptplatform.BuildConfig;
import com.company.ptplatform.Controller.networking.HttpHelper;
import com.company.ptplatform.Controller.networking.HttpHelperBackground;
import com.company.ptplatform.Controller.networking.RetrofitServices;
import com.company.ptplatform.Model.utilits.PreferencesUtils;
import com.company.ptplatform.View.Activity.MainActivity;
import com.company.ptplatform.View.Activity.NotAuthorizedActivity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {

    private static MyApplication instance;
    HttpHelper httpHelper;
    HttpHelperBackground httpHelperBackground;

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    private RetrofitServices httpMethods;
    private Gson gson;
    private SharedPreferences preferences;

    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest  = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + PreferencesUtils.getUserToken())
                    .addHeader("Accept-Language", PreferencesUtils.getLanguage())
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public synchronized RetrofitServices getHttpMethods(Context context) {
        if (httpMethods == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .validateEagerly(true)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .client(createClient(context))
                    .build();

            httpMethods = retrofit.create(RetrofitServices.class);
        }
        return httpMethods;
    }


    public OkHttpClient createClient(Context context) {
        Interceptor mainInterceptor=new Interceptor() {
            @Override
            public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {

                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest
                        .newBuilder()
                        .header("Authorization", "Bearer "+ PreferencesUtils.getUserToken())
                        .header("Accept-Language",PreferencesUtils.getLanguage());

                Request newRequest = builder.build();
             Response resp=  chain.proceed(newRequest);
                int code=resp.code();
                Log.d("res Code","-------------"+code);
                if(code==403){
                    startActivity();
                }
               return resp;
            }
        };
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(mainInterceptor).addInterceptor(logInterceptor).readTimeout(1, TimeUnit.MINUTES).
                connectTimeout(1, TimeUnit.MINUTES).build();


        return okHttpClient;
    }
    private void startActivity() {
      startActivity( new Intent(getApplicationContext(), NotAuthorizedActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME));
        MainActivity.me.finish();
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId(BuildConfig.ONESIGNAL_APP_ID);
//
//        // promptForPushNotifications will show the native Android notification permission prompt.
//        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)
//        OneSignal.promptForPushNotifications();

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
    public synchronized HttpHelperBackground getBackgroundHttpHelper() {
        if (httpHelperBackground == null)
            httpHelperBackground = new HttpHelperBackground();
        return httpHelperBackground;
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
