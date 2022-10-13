package com.qtechnetworks.ptplatform.Model.utilits;

import android.util.Log;

import androidx.multidex.BuildConfig;

public class AppConstants {


    // id to handle the notification in the notification tray

    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static String version= String.valueOf(BuildConfig.VERSION_CODE);
    public static final String ONESIGNAL_APP_ID = "ea046acc-4916-4bb3-8ae6-c7fe2bafaf4d";
    public static final String login_URL = "auth/login";
    public static final int login_TAG = 0;
    public static final String SOCIAL_LOGIN_URL = "auth/social-login";
    public static final int SOCIAL_LOGIN_TAG = 48;

    public static final String signup_URL = "auth/register-user";
    public static final int signup_TAG = 1;

    public static final String signup_coach_URL = "auth/register-coach";
    public static final int signup_coach_TAG = 50;

    public static final String Check_Email_Coach_URL = "auth/verify-email";
    public static final int Check_Email_Coach_TAG = 49;

    public static final String Check_Code_URL = "auth/verify-email/check-code";
    public static final int Check_Code_TAG = 44;

    public static final String Resend_Code_URL = "auth/verify-email/reset-code";
    public static final int Resend_Code_TAG = 45;

    public static final String Check_Email_URL = "auth/verify-email/forgot-password";
    public static final int Check_Email_TAG = 45;

    public static final String Reset_Password_URL = "auth/verify-email/update-password";
    public static final int Reset_Password_TAG = 46;

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
    public static final int exercise_TAG = 18;

    public static final String workout_URL = "section-exercise/exercises";
    public static final int workout_TAG = 19;

    public static final String target_URL = "users/target";
    public static final int target_TAG = 20;

    public static final String fooduser_URL = "foods/user";
    public static final int fooduser_TAG = 21;

    public static final String addfood_URL = "foods/user";
    public static final int addfood_TAG = 22;

    public static final String recipes_URL = "recipes";
    public static final int recipes_TAG = 23;

    public static final String personalWorkout_URL = "users/training/workout";
    public static final int personalWorkout_TAG = 24;

    public static final String personalMeals_URL = "users/training/recipe";
    public static final int personalMeals_TAG = 25;
    public static final String deviceToken_URL = "users/device-token";
    public static final int deviceToken_TAG = 26;
     public static final String SHOW_WORKOUT_VIDS = "users/training/workout/";
    public static final int SHOW_WORKOUT_VIDS_TAG = 27;

    public static final String HEALTHS_URL = "healths";
    public static final int HEALTHS_TAG = 28;

    public static final String BODYMAESUREMENTS_URL = "body-measurements";
    public static final int BODYMAESUREMENTS_TAG = 29;

    public static final String COACH_CALENDAR_URL = "coach-calendar";
    public static final int COACH_CALENDAR_TAG = 30;
    public static final String COACH_CALENDAR_RESERVATION_URL = "coach-calendar/reservation";
    public static final int COACH_CALENDAR_RESERVATION_TAG = 31;

    public static final String COACH_QUESTIONS_URL = "coach-questions";
    public static final int COACH_QUESTIONS_TAG = 32;
    public static final String COACH_QUESTIONS_ANSWER_URL = "coach-questions";
    public static final int COACH_QUESTIONS_ANSWER_TAG = 33;
    public static final String PACKAGES_URL = "packages";
    public static final int PACKAGES_TAG =34 ;
    public static final String CHALLENGES_URL = "challenges";
    public static final int CHALLENGES_TAG = 35;
    public static final String CHALLENGES_VIDEOS_URL = "challenges/videos";
    public static final int CHALLENGES_VIDEOS_TAG =36 ;
    public static final String CHALLENGES_COMPLETE_URL = "challenges/complete";
    public static final int CHALLENGES_COMPLETE_TAG =37 ;
    public static final String UPDATE_AVATAR_URL = "users/update/avatar";
    public static final int UPDATE_AVATAR_TAG =38 ;
    public static final String GET_RESERVATIONS_VIDEO_CHAT_URL = "coach-calendar/user";
    public static final int GET_RESERVATIONS_VIDEO_CHAT_TAG =39 ;
    public static final String GET_PERSONALIZED_URL = "users/training/personal";
    public static final int GET_PERSONALIZED_TAG =40 ;
    public static final String CANCEL_RESERVATION_URL = "coach-calendar/delete-reservation";
    public static final int CANCEL_RESERVATION_TAG =41 ;
    public static final String UPDATE_EMAIL_URL = "users/update/email";
    public static final int UPDATE_EMAIL_TAG =42 ;
    public static final String UPDATE_NAME_URL = "users/update/name";
    public static final int UPDATE_NAME_TAG =43 ;
    public static final String LOGOUT_URL = "users/logout";
    public static final int LOGOUT_TAG =47 ;

    public static final String Personal_Training_Coach_URL = "coaches/personal-training";
    public static final int Personal_Training_Coach_TAG =51 ;

    public static final String Coach_Calendar_URL = "coach-calendar/calendar";
    public static final int Coach_Calendar_TAG =52 ;

    public static final String Coach_Video_Chat_URL = "coach-calendar/coach";
    public static final int Coach_Video_Chat_TAG =53 ;

    public static void Trace(String tag, String msg) {

        if (BuildConfig.DEBUG) {
            Log.d(tag + "", msg + "\n");
        }
    }

}
