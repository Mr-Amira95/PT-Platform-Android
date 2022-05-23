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

    public static final String exercise_videos_URL = "section-exercise/videos";
    public static final int exercise_videos_TAG = 4;

    public static final String section_exercise_URL = "section-exercise";
    public static final int section_exercise_TAG = 5;

    public static final String section_workout_URL = "section-workout";
    public static final int section_workout_TAG = 6;

    public static final String users_coaches_URL = "users/coaches";
    public static final int users_coaches_TAG = 7;

    public static final String workout_videos_URL = "section-workout/videos";
    public static final int workout_videos_TAG = 8;

    public static final String category_exercise_URL = "section-exercise/categories";
    public static final int category_exercise_TAG = 9;

    public static final String category_Workout_URL = "section-workout/categories";
    public static final int category_Workout_TAG = 10;

    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}
