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

    public static final String Add_Favorite_URL = "video-favourites";
    public static final int Add_Favorite_TAG = 11;

    public static final String Add_Workout_URL = "video-workout";
    public static final int Add_Workout_TAG = 12;

    public static final String Add_Log_URL = "video-logs";
    public static final int Add_Log_TAG = 13;

    public static final String support_URL = "ticket/technical_support";
    public static final int support_TAG = 14;

    public static final String feedback_URL = "ticket/feedback";
    public static final int feedback_TAG = 15;

    public static final String food_URL = "foods";
    public static final int food_TAG = 16;

    public static final String Supplement_URL = "supplements";
    public static final int Supplement_TAG = 17;


    public static final String exercise_URL = "section-workout/exercises";
    public static final int exercise_TAG = 11;

    public static final String workout_URL = "section-exercise/exercises";
    public static final int workout_TAG = 12;

    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}
