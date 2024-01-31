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

        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        // Define the allowed time range
        int startHour = 8; // Start time (8 AM)
        int endHour = 17; // End time (5 PM)

        if (!(currentHour >= 17)) {
            // Schedule two random notifications within the allowed time frame
            scheduleNotification(context, startHour, endHour, 0); // First notification
            scheduleNotification(context, startHour, endHour, 1); // Second notification
        }
    }

    private static void scheduleNotification(Context context, int startHour, int endHour, int requestCode) {
        Random random = new Random();

        // Create a calendar instance for the random time within allowed hours
        Calendar calendar = Calendar.getInstance();
        int randomHour = random.nextInt(endHour - startHour) + startHour;
        int randomMinute = random.nextInt(60);
        calendar.set(Calendar.HOUR_OF_DAY, randomHour);
        calendar.set(Calendar.MINUTE, randomMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Check if the generated time is in the past, add an hour if so
        while (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            if (calendar.get(Calendar.HOUR_OF_DAY) == 16) {
                break;
            }
        }

        // Create an Intent for the BroadcastReceiver
        Intent intent = new Intent(context, VideoReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode, // Use requestCode to differentiate the pending intents
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        // Get the AlarmManager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Cancel any existing alarms with this PendingIntent and schedule a new one
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }

        // Log the scheduled times for debugging purposes
        Log.d("VideoReminderScheduler", "Scheduled notification at: " + calendar.getTime().toString());
    }
}
