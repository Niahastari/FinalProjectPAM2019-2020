package com.rickysn1007.moviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.reminder.DailyPreference;
import com.rickysn1007.moviecatalogue.reminder.DailyReceiver;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    DailyReceiver dailyReceiver;
    DailyPreference dailyPreference;
    SharedPreferences daily_reminder;
    SharedPreferences.Editor edtDailyReminder;

    String TYPE_DAILY = "reminderDaily";
    String DAILY_REMINDER = "dailyReminder";
    String KEY_DAILY_REMINDER = "Daily";

    String timeDaily = "09:00";
    private SwitchCompat swDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dailyReceiver = new DailyReceiver();
        dailyPreference = new DailyPreference(this);
        Button language = findViewById(R.id.btnLanguage);
        language.setOnClickListener(this);
        Button about = findViewById(R.id.btnAbout);
        about.setOnClickListener(this);
        swDaily = findViewById(R.id.daily_switch);
        setPreference();
        setDaily();
    }

    private void dailyOn() {
        String message = getResources().getString(R.string.daily_reminder_title);
        dailyPreference.setTimeDaily(timeDaily);
        dailyPreference.setDailyMessage(message);
        dailyReceiver.setAlarm(SettingActivity.this, TYPE_DAILY, timeDaily, message);
    }

    private void dailyOff() {
        dailyReceiver.cancelNotif(SettingActivity.this);
    }

    private void setPreference() {
        daily_reminder = getSharedPreferences(DAILY_REMINDER, MODE_PRIVATE);
        boolean checkDailyReminder = daily_reminder.getBoolean(KEY_DAILY_REMINDER, false);
        swDaily.setChecked(checkDailyReminder);
    }

    public void setDaily() {
        edtDailyReminder = daily_reminder.edit();
        swDaily.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, true);
                edtDailyReminder.apply();
                dailyOn();
            } else {
                edtDailyReminder.putBoolean(KEY_DAILY_REMINDER, false);
                edtDailyReminder.commit();
                dailyOff();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAbout) {
            Intent mIntent = new Intent(SettingActivity.this, AboutActivity.class);
            startActivity(mIntent);
        }
        else if (v.getId() == R.id.btnLanguage) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }
}
