package com.company.ptplatform.Model.utilits;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.onesignal.OSMutableNotification;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal.OSRemoteNotificationReceivedHandler;
import com.onesignal.OneSignalApiResponseHandler;
import com.company.ptplatform.View.Activity.MainActivity;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * FirebaseInstanceIdService Gets FCM instance ID token from Firebase Cloud Messaging Server
 */
@SuppressWarnings("unused")
public class MyFirebaseInstanceIDService extends  FirebaseMessagingService implements OSRemoteNotificationReceivedHandler, OneSignalApiResponseHandler{

    //*********** Called whenever the Token is Generated or Refreshed ********//

    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent notificationReceivedEvent) {
        OSNotification notification = notificationReceivedEvent.getNotification();

        notification.getGroupedNotifications();
        Log.d("remoteReceived","------------");
        // Example of modifying the notification's accent color
        OSMutableNotification mutableNotification = notification.mutableCopy();
        mutableNotification.setExtender(builder -> {
            // Sets the accent color to Green on Android 5+ devices.
            // Accent color controls icon and action buttons on Android 5+. Accent color does not change app title on Android 10+

            builder.setColor(new BigInteger("FF00FF00", 16).intValue());
            // Sets the notification Title to Red
            Spannable spannableTitle = new SpannableString(notification.getTitle());
            spannableTitle.setSpan(new ForegroundColorSpan(Color.RED),0,notification.getTitle().length(),0);
            builder.setContentTitle(spannableTitle);
            // Sets the notification Body to Blue
            Spannable spannableBody = new SpannableString(notification.getBody());
            spannableBody.setSpan(new ForegroundColorSpan(Color.BLUE),0,notification.getBody().length(),0);
            builder.setContentText(spannableBody);

            //Force remove push from Notification Center after 30 seconds
            builder.setTimeoutAfter(30000);

            JSONObject data = notification.getAdditionalData();

            Intent notificationIntent;

            notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

//            try {
//                notificationIntent.putExtra("flag", data.get("custom").);
//                notificationIntent.putExtra("id", data.getString("id"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

            Log.d("Notification_Date", data.toString());

            PendingIntent pendingIntent = PendingIntent.getActivity((context), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT| PendingIntent.FLAG_ONE_SHOT);

            builder.setContentIntent(pendingIntent);
            return builder;
        });

//        Log.i("OneSignalExample", "Received Notification Data: " + data);

//        try {
//            if(data!=null){
//
//                Intent i = new Intent(context, MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.putExtra("flag", data.getString("flag"));
//                i.putExtra("id", data.getString("id"));
//                startActivity(i);
//                ((MainActivity)context).finish();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        getorder(data.getString("id"));
//         If complete isn't call within a time period of 25 seconds, OneSignal internal logic will show the original notification
//         To omit displaying a notification, pass `null` to complete()
        notificationReceivedEvent.complete(mutableNotification);

    }


    @Override
    public void onSuccess(String s) {

    }

    @Override
    public void onFailure(int i, String s, Throwable throwable) {

    }
}