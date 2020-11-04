package com.example.moviecatalog.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalog.R;

import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DailyReminderReceiver extends BroadcastReceiver {

    private static final int ID_NOTIFIKASI = 101;
    public static final String EXTRA_MESSAGE_DAILY = "dailyreminder";

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra(EXTRA_MESSAGE_DAILY);
        String title = "Daily Reminder ";

        Toast.makeText(context, title + " : " + message, Toast.LENGTH_SHORT).show();
        showNotifikasi(context, ID_NOTIFIKASI, title, message);

    }

    public void showNotifikasi(Context context, int id, String title, String description) {

        String channelID = "Channel_ID";
        String channelName = "Daily_Reminder_Alarm";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_video_library_black_24dp)
                .setContentTitle(title)
                .setContentText(description)
                .setColor(ContextCompat.getColor(context, R.color.colorWhite))
                .setVibrate(new long[]{1000, 1100, 1000, 1100})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1100, 1000, 1100});
            builder.setChannelId(channelID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }
    }

    public void setNotifikasi(Context context, String time, String message) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);
        intent.putExtra(EXTRA_MESSAGE_DAILY, message);

        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIFIKASI, intent, 0);

        assert alarmManager != null;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Log.d(TAG, "setNotifikasi: Alarm on");

    }

    public void cancelAlarm(Context context) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, DailyReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_NOTIFIKASI, intent, 0);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "reminder canceled", Toast.LENGTH_SHORT).show();
    }
}
