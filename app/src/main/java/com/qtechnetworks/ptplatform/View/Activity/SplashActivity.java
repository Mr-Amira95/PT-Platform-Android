package com.qtechnetworks.ptplatform.View.Activity;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.onesignal.OneSignal;
import com.qtechnetworks.ptplatform.BuildConfig;
import com.qtechnetworks.ptplatform.Controller.networking.CallBack;
import com.qtechnetworks.ptplatform.Model.Beans.device.DeviceToken;
import com.qtechnetworks.ptplatform.Model.basic.MyApplication;
import com.qtechnetworks.ptplatform.Model.utilits.AppConstants;
import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity implements CallBack {

    public static int SPLASH_TIME_OUT=4000;
    String flag = " ", id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        switchLocal(this);

        flag = getIntent().getStringExtra("flag");
        id = getIntent().getStringExtra("id");

        // OneSignal Initialization
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(BuildConfig.ONESIGNAL_APP_ID);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferencesUtils.getUserToken().equalsIgnoreCase("-1")){
                    startActivity(new Intent(SplashActivity.this, ChoosingActivity.class));
                    finish();
                } else {
                    devicetoken();
                }

            }
        },SPLASH_TIME_OUT);

    }

    private void devicetoken() {

        HashMap<String,Object> tokenMap = new HashMap<>();

        tokenMap.put("player_id", PreferencesUtils.getPlayerId());
        tokenMap.put("platform", "android");
        tokenMap.put("timezone", TimeZone.getDefault().toString());
        tokenMap.put("app_version", BuildConfig.VERSION_NAME);

        MyApplication.getInstance().getBackgroundHttpHelper().setCallback(this);
        MyApplication.getInstance().getBackgroundHttpHelper().Post(SplashActivity.this, AppConstants.deviceToken_URL,AppConstants.deviceToken_TAG , DeviceToken.class,tokenMap);
    }

    private void switchLocal(Context context) {
        if (PreferencesUtils.getLanguage().equalsIgnoreCase(""))
            return;
        Resources resources = context.getResources();
        Locale locale = new Locale(PreferencesUtils.getLanguage());
        Locale.setDefault(locale);
        android.content.res.Configuration config = new
                android.content.res.Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(int tag, boolean isSuccess, Object result) {
        Intent i = new Intent(SplashActivity.this,MainActivity.class);
        i.putExtra("flag", flag);
        i.putExtra("id", id);
        startActivity(i);
        finish();
    }

    @Override
    public void onError(Throwable e) {
//        if(((HttpException) e).message().equalsIgnoreCase("Unauthorized")){
//            PreferencesUtils.clearDefaults(SplashActivity.this);
//            startActivity(new Intent(SplashActivity.this, SignInActivity.class));
//        }
    }

    @Override
    public void onComplete() {
//        Intent i = new Intent(SplashActivity.this, MainActivity.class);
//        startActivity(i);
//        finish();
    }
}