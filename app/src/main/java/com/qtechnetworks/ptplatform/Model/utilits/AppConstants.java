package com.qtechnetworks.ptplatform.Model.utilits;

import android.util.Log;

import androidx.multidex.BuildConfig;

public class AppConstants {


    // id to handle the notification in the notification tray

    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static String version= String.valueOf(BuildConfig.VERSION_CODE);

    public static final String login_URL = "auth/login";
    public static final int login_TAG = 0;

    public static final String signup_URL = "auth/register-user";
    public static final int signup_TAG = 1;

    public static final String news_URL = "news-feed";
    public static final int news_TAG = 2;

    public static final String banner_URL = "banners";
    public static final int banner_TAG = 3;


    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}
