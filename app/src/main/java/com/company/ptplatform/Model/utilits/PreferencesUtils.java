package com.company.ptplatform.Model.utilits;


import static com.company.ptplatform.Model.utilits.AppConstants.Trace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.company.ptplatform.Model.Beans.Coach.Datum;
import com.company.ptplatform.Model.Beans.RegisterAndLogin.User;
import com.company.ptplatform.Model.basic.MyApplication;


import java.util.Locale;


public class PreferencesUtils {

    private Activity activity;

    // Constructor
    public PreferencesUtils(Activity activity) {
        this.activity = activity;
    }

    public static void putString(String key, String val) {
        MyApplication.getInstance().getPreferences().edit().putString(key, val).apply();
        Trace("Saveing " + key, val + "");
    }

    public static void putInt(String key, int val) {
        MyApplication.getInstance().getPreferences().edit().putInt(key, val).apply();
    }

    public static void putBoolean(String key, boolean val) {
        MyApplication.getInstance().getPreferences().edit().putBoolean(key, val).apply();
    }

    public static String getString(String key, String val) {
        Trace("getString " + key, val + " Default");
        return MyApplication.getInstance().getPreferences().getString(key, val);
    }

    public static int getInt(String key, int val) {
        return MyApplication.getInstance().getPreferences().getInt(key, val);
    }

    public static boolean getBoolean(String key, boolean val) {
        return MyApplication.getInstance().getPreferences().getBoolean(key, val);
    }

    public static boolean getNotificationEnabled() {
        return PreferencesUtils.getBoolean(PrefKeys.notificationEnabled, true);

    }

    public static String getUserID() {
        return PreferencesUtils.getString(PrefKeys.userID, "-1");

    }

    public static String getUserType() {

        if (PreferencesUtils.getString(PrefKeys.type, "-1").equalsIgnoreCase("user")){
            return "trainee";
        } else {
            return PreferencesUtils.getString(PrefKeys.type, "-1");
        }
    }

    public static void setUserType(String type) {
        PreferencesUtils.putString(PrefKeys.type, type);
    }

    public static String getUserToken() {
        return PreferencesUtils.getString(PrefKeys.token, "-1");

    }

    public static String getLanguage() {
        return PreferencesUtils.getString(PrefKeys.language, "en");
    }

    public static void setLanguage(String lang) {
        PreferencesUtils.set(PrefKeys.language, lang);
    }

    public static void setUserToken(String token) {
        PreferencesUtils.set(PrefKeys.token, token);
    }

    public static void setUsertype(String accounttype) {
        PreferencesUtils.set(PrefKeys.accounttype, accounttype);
    }

    public static String getUserName() {
        return PreferencesUtils.getString(PrefKeys.username, "");

    }

    public static String getintro() {
        return PreferencesUtils.getString(PrefKeys.valintro, "");

    }
    public static void setintro(String val) {
         PreferencesUtils.set(PrefKeys.valintro, val);

    }

    public static String getCountry() {
        return PreferencesUtils.getString(PrefKeys.countryID, "1");

    }

    public static String getUsertype() {
        return PreferencesUtils.getString(PrefKeys.accounttype, "");

    }

    public static String getUserGender() {
        return PreferencesUtils.getString(PrefKeys.userGeneder, "");

    }

    public static String getUserEmail() {
        return PreferencesUtils.getString(PrefKeys.userEmail, "");

    }

    public static String getUserPhone() {
        return PreferencesUtils.getString(PrefKeys.userPhone, "");

    }
    public static String getPlayerId() {
        return PreferencesUtils.getString(PrefKeys.playerId, "");

    }

    public static void setPlayerId(String playerId) {
        PreferencesUtils.set(PrefKeys.playerId, playerId);
    }

    public static void setLocale(String lang, Activity activity) {
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
//        activity.recreate();
    }

    public static void setDefaults(String key, String value, Context context) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.apply();
            Trace("Set Default: " + key, "" + value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static User getUser(Context c) {

        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            return gn.fromJson(getDefaults("user", c), User.class);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("getting user ", "");
            return null;
        }
    }

    public static void setUser(User value, Context c) {
        String json = "";
        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            json = gn.toJson(value);
            setDefaults("user", json, c);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("setting user", json);

        }
    }

    public static Datum getCoach(Context c) {

        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            return gn.fromJson(getDefaults("coach", c), Datum.class);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("getting Coach ", "");
            return null;
        }
    }

    public static void setCoach(Datum value, Context c) {
        String json = "";
        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            json = gn.toJson(value);
            setDefaults("coach", json, c);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("setting Coach", json);

        }
    }

    public static com.company.ptplatform.Model.Beans.PersonalCoach.Datum getTrainee(Context c) {

        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            return gn.fromJson(getDefaults("trainee", c), com.company.ptplatform.Model.Beans.PersonalCoach.Datum.class);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("getting trainee ", "");
            return null;
        }
    }

    public static void setTrainee(com.company.ptplatform.Model.Beans.PersonalCoach.Datum value, Context c) {
        String json = "";
        try {
            GsonBuilder gsb = new GsonBuilder();
            Gson gn = gsb.create();
            json = gn.toJson(value);
            setDefaults("trainee", json, c);
        } catch (Exception e) {
            e.printStackTrace();
            Trace("setting Coach", json);
        }
    }

    public static String getDefaults(String key, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, "-1");
        Trace("Get Defaults", "" + value);
        return value;
    }

    public static void clearDefaults(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static void set(String key, String value) {


        MyApplication.getInstance().getPreferences().edit().putString(key, value).commit();

    }

}
