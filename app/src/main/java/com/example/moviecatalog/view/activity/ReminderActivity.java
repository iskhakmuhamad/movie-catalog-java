package com.example.moviecatalog.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.moviecatalog.R;
import com.example.moviecatalog.preference.ReminderPreference;
import com.example.moviecatalog.reminder.DailyReminderReceiver;
import com.example.moviecatalog.reminder.ReleaseReminderReceiver;

import java.util.Objects;

import static com.example.moviecatalog.preference.ReminderPreference.DAILY_REMINDER_KEY;
import static com.example.moviecatalog.preference.ReminderPreference.RELEASE_REMINDER_KEY;

public class ReminderActivity extends AppCompatActivity {

    private static final String STATE_DAILY_REMINDER = "state_daily_reminder";
    private static final String STATE_RELEASE_REMINDER = "state_release_reminder";
    private static final String TAG ="REMINDER_ACTIVITY" ;
    private Switch svDaily, svRelease;
    private DailyReminderReceiver dailyReminderReceiver;
    private ReleaseReminderReceiver releaseReminderReceiver;

    private ReminderPreference reminderPreference;

    private SharedPreferences preferencesDaily, preferecesRelease;
    private SharedPreferences.Editor editorDaily, editorRelease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        svDaily = findViewById(R.id.sv_daily_Remind);
        svRelease = findViewById(R.id.sv_release_remind);

        svDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editorDaily = preferencesDaily.edit();
                if (isChecked) {
                    editorDaily.putBoolean(STATE_DAILY_REMINDER, true);
                    editorDaily.apply();
                    setOnDailyReminder();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.daily_reminder_on), Toast.LENGTH_SHORT).show();
                } else {
                    editorDaily.putBoolean(STATE_DAILY_REMINDER, false);
                    editorDaily.apply();
                    setOffDailyReminder();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.daily_reminder_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        svRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                editorRelease = preferecesRelease.edit();
                if (isChecked) {
                    editorRelease.putBoolean(STATE_RELEASE_REMINDER, true);
                    editorRelease.apply();
                    setOnReleaseReminder();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.release_reminder_on), Toast.LENGTH_SHORT).show();
                } else {
                    editorRelease.putBoolean(STATE_RELEASE_REMINDER, false);
                    editorRelease.apply();
                    setOffReleaseReminder();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.release_reminder_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dailyReminderReceiver = new DailyReminderReceiver();
        releaseReminderReceiver = new ReleaseReminderReceiver();
        reminderPreference = new ReminderPreference(this);

        preferencesDaily = getSharedPreferences(DAILY_REMINDER_KEY, MODE_PRIVATE);
        boolean preferencesDailyBoolean = preferencesDaily.getBoolean(STATE_DAILY_REMINDER, false);
        svDaily.setChecked(preferencesDailyBoolean);

        preferecesRelease = getSharedPreferences(RELEASE_REMINDER_KEY, MODE_PRIVATE);
        boolean preferecesReleaseBoolean = preferecesRelease.getBoolean(STATE_RELEASE_REMINDER, false);
        svRelease.setChecked(preferecesReleaseBoolean);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setOnDailyReminder() {
        String messageDaily = getResources().getString(R.string.daily_notif_message);
        String timeDaily = "07:00";

        reminderPreference.setTimeDailyReminder(timeDaily);
        reminderPreference.setMessageDailyReminder(messageDaily);

        dailyReminderReceiver.setNotifikasi(ReminderActivity.this, timeDaily, messageDaily);
        Log.d(TAG, "setOnReleaseReminder: daily reminder on");

        svDaily.setText(getResources().getString(R.string.txt_on));
    }

    private void setOnReleaseReminder() {
        String messageRelease = getResources().getString(R.string.release_notif_message);
        String timeRelease = "08:00";
        reminderPreference.setTimeReleaseReminder(timeRelease);
        reminderPreference.setMessageReleaseReminder(messageRelease);

        releaseReminderReceiver.checkMovieRelease(getApplicationContext());
        svRelease.setText(getResources().getString(R.string.txt_on));
    }

    private void setOffDailyReminder() {
        dailyReminderReceiver.cancelAlarm(getApplicationContext());
        Log.d(TAG, "setOffDailyReminder: daily reminder canceled");
        svDaily.setText(getResources().getString(R.string.txt_off));
    }

    private void setOffReleaseReminder() {
        releaseReminderReceiver.setCancelReleaseNotification(getApplicationContext());
        Log.d(TAG, "setOffReleaseReminder: release reminder canceled");
        svRelease.setText(getResources().getString(R.string.txt_off));
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

}
