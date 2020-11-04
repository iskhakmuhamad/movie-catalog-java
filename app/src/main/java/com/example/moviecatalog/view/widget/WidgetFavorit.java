package com.example.moviecatalog.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.moviecatalog.R;

import java.util.Objects;

public class WidgetFavorit extends AppWidgetProvider {

    public static final String WIDGET_TOAST = "widgettoast";
    public static final String WIDGET_ITEM = "widgetitem";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context, StackWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_favorit);
        views.setRemoteAdapter(R.id.st_fav_widget, intent);
        views.setEmptyView(R.id.st_fav_widget, R.id.tv_empty_widget);

        Intent favIntent = new Intent(context, WidgetFavorit.class);
        favIntent.setAction(WidgetFavorit.WIDGET_TOAST);
        favIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        favIntent.setData(Uri.parse(favIntent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, favIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.st_fav_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (Objects.equals(intent.getAction(), WIDGET_TOAST)) {
            int index = intent.getIntExtra(WIDGET_ITEM, 0);
            Toast.makeText(context,"Touched at View " + index,Toast.LENGTH_SHORT).show();
        }
    }

}

