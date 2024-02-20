package com.example.spomoo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.spomoo.mainscreen.MainActivity;

public class MidnightReset extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MidnightReset", "onReceive called");
        // Reset your boolean value to false here
        MainActivity.resetVideoReminderFlag();
    }
}