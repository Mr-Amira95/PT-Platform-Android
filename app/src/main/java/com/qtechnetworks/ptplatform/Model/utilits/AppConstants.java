package com.qtechnetworks.ptplatform.Model.utilits;

import android.util.Log;

import androidx.multidex.BuildConfig;

public class AppConstants {


    // id to handle the notification in the notification tray

    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static String version= String.valueOf(BuildConfig.VERSION_CODE);

    public static final String signuptalent_URL = "auth/register/talent";
    public static final int signuptalent_TAG = 0;


    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}
