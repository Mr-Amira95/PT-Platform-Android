package com.qtechnetworks.ptplatform.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

import com.qtechnetworks.ptplatform.Model.utilits.PreferencesUtils;
import com.qtechnetworks.ptplatform.R;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    public static int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        initial();

    }


    public void initial(){


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (PreferencesUtils.getUserToken().equalsIgnoreCase("-1")){
                    startActivity(new Intent(SplashActivity.this,ChoosingActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    finish();
                }



            }
        },SPLASH_TIME_OUT);

    }

    private void switchLocal(Context context, String lcode) {
        if (lcode.equalsIgnoreCase(""))
            return;
        Resources resources = context.getResources();
        Locale locale = new Locale(lcode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new
                android.content.res.Configuration();
        config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        //restart base activity

    }

}