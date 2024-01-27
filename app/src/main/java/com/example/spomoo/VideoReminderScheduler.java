package com.example.spomoo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Random;

/*
from AlarmManager documentation: "If an alarm is delayed (e.g., by system sleep), a skipped repeat will be delivered as soon as possible."
-> consider this while testing
 */

public class VideoReminderScheduler {

    public static void setRandomNotification(Context context) {
        Log.d("VideoReminderScheduler", "setRandomNotification called");

        // Create a random time between 8am and 5pm
        int randomHour = new Random().nextInt(10) + 8; // Random number between 8 and 17
        int randomMinute = new Random().nextInt(60); // Random number between 0 and 59

        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 34);
        calendar.set(Calendar.SECOND, 0);

        // Create an Intent for the BroadcastReceiver
        Intent intent = new Intent(context, VideoReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set a repeating alarm that triggers once a day
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }


}
