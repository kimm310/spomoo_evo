package com.example.spomoo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.spomoo.mainscreen.MainActivity;

public class VideoReminder extends BroadcastReceiver {

    public static final String CHANNEL_ID = "VideoNotificationChannelID";
    private final int notificationID = 6;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("VideoReminder", "onReceive called");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create notification channel
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Video",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            // Initialise intent to open VideoList
            Intent notificationIntent = new Intent(context, VideoList.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

            // Define all parts of local notification (icon, title, description text, priority, channel ID)
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.baseline_ondemand_video_24)
                    .setContentTitle("Übungserinnerung")
                    .setContentText("Schau dir ein Video an!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            // Set listener for notification on ID 6
            notificationManager.notify(notificationID, builder.build());

        }
    }
}
