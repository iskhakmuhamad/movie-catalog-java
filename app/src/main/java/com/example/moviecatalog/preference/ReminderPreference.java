package com.example.moviecatalog.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {
    private static final String PREFERENCE_NAME = "reminder_preference";
    public static final String DAILY_REMINDER_KEY = "daily_reminder";
    private static final String MESSAGE_DAILY_REMINDEY_KEY = "daily_reminder_message";
    public static final String RELEASE_REMINDER_KEY = "release_reminder";
    private static final String MESSAGE_RELEASE_REMINDER_KEY = "release_reminder_message";

    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public ReminderPreference(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
        );

        editor = sharedPreferences.edit();
    }

    public void setTimeDailyReminder(String time){
        editor.putString(DAILY_REMINDER_KEY,time);
        editor.commit();
    }

    public void setMessageDailyReminder(String messageDailyReminder){

        editor.putString(MESSAGE_DAILY_REMINDEY_KEY , messageDailyReminder);

    }

    public void  setTimeReleaseReminder(String timeReleaseReminder){

        editor.putString(RELEASE_REMINDER_KEY, timeReleaseReminder);
        editor.commit();

    }

    public  void  setMessageReleaseReminder(String messageReleaseReminder){

        editor.putString(MESSAGE_RELEASE_REMINDER_KEY, messageReleaseReminder);
    }


}
