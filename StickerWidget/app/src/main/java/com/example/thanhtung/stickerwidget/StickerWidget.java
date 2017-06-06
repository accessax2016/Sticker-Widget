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
//    public static final String ACTION_WIDGET_CLICK = "com.example.thanhtung.stickerwidget.StickerWidget.ACTION_WIDGET_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, StickerWidgetConfigureActivity.class);
            //intent.setAction(ACTION_WIDGET_CLICK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
            views.setOnClickPendingIntent(R.id.rlAppWidget, pendingIntent);

//            Sticker sticker = WidgetPrefs.loadPref(context, appWidgetId);
//            views.setTextViewText(R.id.appwidget_text, Html.fromHtml(addItalicTag(sticker.isItalic(), addBoldTag(sticker.isBold(), replaceNewLine(sticker.getTitle())))));
//            views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
//            views.setTextColor(R.id.appwidget_text, sticker.getColor());
//            views.setImageViewResource(R.id.appwidget_background, sticker.getBackground());
//            views.setImageViewResource(R.id.appwidget_tag, sticker.getTag());
//            views.setImageViewResource(R.id.appwidget_icon, sticker.getIcon());

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
}

