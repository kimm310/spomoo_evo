package com.example.spomoo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.spomoo.mainscreen.MainActivity;

public class VideoNotificationService {

    private final Context context;
    private NotificationManager notificationManager;


    public VideoNotificationService(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification() {
        Log.d("VideoNotificationService", "showNotification called");
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(
                context,
                /*requestCode*/ 0,
                activityIntent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0       // so I know I should change the flag so the notif can actually go away once clicked, but HOW?
        );

        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("video_channel", "Video", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            builder = new Notification.Builder(context, "video_channel");
        } else {
            builder = new Notification.Builder(context);
        }

        builder.setContentIntent(activityPendingIntent);        // for some reason, it doesn't work without this line of code even tho it's already in 49
        builder.setAutoCancel(true);                            // WHY DOES IT ONLY WORK WHEN I WRITE IT LIKE THIS??? NVM it doesn't work anymore for some goddamn reason
        builder.setSmallIcon(R.drawable.baseline_ondemand_video_24)
                .setContentTitle("Übungserinnerung")
                .setContentText("Schau dir ein Video an!");
                //.setContentIntent(activityPendingIntent);
                //.setAutoCancel(true);    // leads to crash for some reason


        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.notify(/*notificationId*/ 1, builder.build());
    }
}

