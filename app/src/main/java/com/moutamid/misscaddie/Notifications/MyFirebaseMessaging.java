package com.moutamid.misscaddie.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.moutamid.misscaddie.CaddieDashboardActivity;
import com.moutamid.misscaddie.Dashboard_Golfer;
import com.moutamid.misscaddie.R;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sent = remoteMessage.getData().get("sent");

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //SharedPreferencesManager prefs = new SharedPreferencesManager(getApplicationContext());
        //boolean notify = prefs.retrieveBoolean("isNotify", false);
        if (firebaseUser != null && sent.equals(firebaseUser.getUid())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sendOreoNotification(remoteMessage);
            } else {
                sendNotification(remoteMessage);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendOreoNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String type = remoteMessage.getData().get("type");

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent;
        if (type.equals("Caddie")) {
            intent = new Intent(this, CaddieDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else {
            intent = new Intent(this, Dashboard_Golfer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent,
                PendingIntent.FLAG_IMMUTABLE);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = null;
        //   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        builder = oreoNotification.getOreoNotification(title, body, pendingIntent, defaultSound, icon);
        // }

        int i = 0;
        if (j > 0) {
            i = j;
        }

        oreoNotification.getManager().notify(i, builder.build());
    }

    @SuppressWarnings("deprecation")
    private void sendNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String type = remoteMessage.getData().get("type");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        NotificationCompat.Builder builder = null;
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent= null;
        if (type.equals("Caddie")) {
            intent = new Intent(this, CaddieDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else {

            intent = new Intent(this, Dashboard_Golfer.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this, j, intent,
                    PendingIntent.FLAG_IMMUTABLE);
        }else {
            pendingIntent = PendingIntent.getActivity(this, j, intent,
                    PendingIntent.FLAG_ONE_SHOT);
        }
//                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if (type.equals("Caddie")) {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_green)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setContentIntent(pendingIntent);
        }else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.logo_yellow)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSound)
                    .setContentIntent(pendingIntent);
        }
        NotificationManager noti = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0) {
            i = j;
        }

        noti.notify(i, builder.build());
    }
}
