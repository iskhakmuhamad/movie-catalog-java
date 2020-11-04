package com.example.moviecatalog.view.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;


import com.example.moviecatalog.R;
import com.example.moviecatalog.db.AppDatabase;
import com.example.moviecatalog.db.DbDataModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<DbDataModel> dbFavData = new ArrayList<>();
    private final Context context;
    private AppDatabase appDatabase;

    StackRemoteViewFactory(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (appDatabase == null) {
            appDatabase = AppDatabase.getAppDatabase(context);
            Log.d(TAG, "onDataSetChanged: get database");
        }
        dbFavData.addAll(appDatabase.favDataDao().getFavoriteData());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return dbFavData.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item_fav);

        URL url;
        Bitmap bitmap = null;
        String baseUrl = "https://image.tmdb.org/t/p/w500/";

        try {
            url = new URL(baseUrl + dbFavData.get(i).getImage());

            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            Log.e(TAG, "getViewAt: Fail Download " + e);
        }

        remoteViews.setImageViewBitmap(R.id.img_fav_widget, bitmap);

        Bundle bundle = new Bundle();
        bundle.putInt(WidgetFavorit.WIDGET_ITEM, i);

        Intent fillIntent = new Intent();
        fillIntent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.img_fav_widget, fillIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
