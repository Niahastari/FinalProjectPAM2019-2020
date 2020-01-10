package com.rickysn1007.moviecatalogue.reminder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;


public class DailyPreference {
    private final static String PREFERENCE = "reminderPreferences";
    private final static String KEY_DAILY = "DailyReminder";
    private final static String KEY_MESSAGE_DAILY = "messageDaily";
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public DailyPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setTimeDaily(String time) {
        editor.putString(KEY_DAILY, time);
        editor.commit();
    }

    public void setDailyMessage(String message) {
        editor.putString(KEY_MESSAGE_DAILY, message);
    }
}

