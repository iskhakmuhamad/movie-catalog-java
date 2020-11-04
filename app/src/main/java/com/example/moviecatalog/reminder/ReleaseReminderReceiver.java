package com.example.moviecatalog.reminder;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.moviecatalog.R;
import com.example.moviecatalog.datamodel.MoviesItem;
import com.example.moviecatalog.datamodel.MoviesResponse;
import com.example.moviecatalog.rest.MoviesService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ReleaseReminderReceiver extends BroadcastReceiver {

    private static final String EXTRA_ID = "extra_id";
    private static final String EXTRA_TITLE = "extra_title";

    private List<MoviesItem> listMovie = new ArrayList<>();
    private List<MoviesItem> mList = new ArrayList<>();

    private int id;
    private int delay;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        id = intent.getIntExtra(EXTRA_ID, 0);
        String title = intent.getStringExtra(EXTRA_TITLE);
        setReleaseMovie(context,id,title);
    }

    public void checkMovieRelease(final Context context) {
        Log.d(TAG, "on checkMovieRelease");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        final String dateToday = dateFormat.format(date);

        MoviesService moviesService = new MoviesService();
        moviesService.getMoviesApi().getMoviesRelease(dateToday, dateToday).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.body() != null) {
                    List<MoviesItem> movieRelease = response.body().getResults();
                    mList.clear();
                    mList.addAll(movieRelease);

                    for (MoviesItem moviesItem : mList) {
                        if (moviesItem.getReleaseDate().equals(dateToday)) {
                            Log.d(TAG, "onResponse: getEqualsReleaseDate");
                            listMovie.add(moviesItem);
                        } else {
                            Log.d(TAG, "onResponse: dateDidntEquals");
                        }
                    }
                    setReleaseNotifications(context, listMovie);
                } else {
                    Log.d(TAG, "onResponse: Not found");
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });

    }

    private void setReleaseNotifications(Context context, List<MoviesItem> listMovie) {
        Log.d(TAG, "on setReleaseNotifications");

        for (MoviesItem movieItem : listMovie) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, ReleaseReminderReceiver.class);
            intent.putExtra(EXTRA_ID, id);
            intent.putExtra(EXTRA_TITLE, movieItem.getTitle());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            assert alarmManager != null;
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY, pendingIntent);

            id = id + 1;
            delay = delay + 2000;

            Log.d(TAG, "push id " + id + " with title " + movieItem.getTitle());

        }
    }

    public void setReleaseMovie(Context context, int id, String title) {

        Log.d(TAG, "setReleaseMovie: called");

        String channelId = "Channel_Release_Daily";
        String channelName = "Daily_Release_alarm";

        @SuppressLint("ServiceCast")
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_video_library_black_24dp)
                .setContentTitle(title)
                .setContentText("Release Movie")
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1100, 1000, 1100})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{1000, 1100, 1000, 1100});
            builder.setChannelId(channelId);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(id, notification);
        }

    }

    public void setCancelReleaseNotification(Context context){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 202 , intent, PendingIntent.FLAG_CANCEL_CURRENT);
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);

    }
}
