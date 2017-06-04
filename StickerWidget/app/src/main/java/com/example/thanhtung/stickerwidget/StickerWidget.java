package com.example.thanhtung.stickerwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link StickerWidgetConfigureActivity StickerWidgetConfigureActivity}
 */
public class StickerWidget extends AppWidgetProvider {
//    public static Context the_context ;
//    public static AppWidgetManager the_appWidgetManager ;
//    public static int the_appWidgetId;
//
//    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
//                                int appWidgetId) {
//
//        the_context = context;
//        the_appWidgetManager = appWidgetManager;
//        the_appWidgetId = appWidgetId;
//
//        // Construct the RemoteViews object
//        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
//        views.setTextViewText(R.id.appwidget_text, sticker.getTitle());
//        views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
//        views.setTextColor(R.id.appwidget_text, sticker.getColor());
//
//        Intent intent = new Intent(context, StickerWidgetConfigureActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        views.setOnClickPendingIntent(R.id.rlBackground, pendingIntent);
//
//        // Instruct the widget manager to update the widget
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, StickerWidgetConfigureActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
            views.setOnClickPendingIntent(R.id.rlAppWidget, pendingIntent);

//            Sticker sticker = WidgetPrefs.loadPref(context, appWidgetId);
//            views.setTextViewText(R.id.appwidget_text, sticker.getTitle());
//            views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
//            views.setTextColor(R.id.appwidget_text, sticker.getColor());

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            WidgetPrefs.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

//    public static void setValueWidget(RemoteViews views, Sticker sticker) {
//        views.setTextViewText(R.id.appwidget_text, sticker.getTitle());
//        views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
//        views.setTextColor(R.id.appwidget_text, sticker.getColor());
//        the_appWidgetManager.updateAppWidget(the_appWidgetId, views);
//    }
}

