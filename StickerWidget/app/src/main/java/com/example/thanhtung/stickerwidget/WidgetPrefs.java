package com.example.thanhtung.stickerwidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by Thanh Tung on 6/4/2017.
 */

public class WidgetPrefs {
    private static final String PREFS_NAME = "com.example.thanhtung.stickerwidget.StickerWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    // Write the prefix to the SharedPreferences object for this widget
    static void savePref(Context context, int appWidgetId, String title, boolean isBold, boolean isItalic, int textsize, int color) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, title);
        prefs.putBoolean(PREF_PREFIX_KEY + appWidgetId +"_BOLD", isBold);
        prefs.putBoolean(PREF_PREFIX_KEY + appWidgetId +"_ITALIC", isItalic);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", textsize);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_TextColor", color);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static Sticker loadPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String title = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        boolean isBold = prefs.getBoolean(PREF_PREFIX_KEY + appWidgetId +"_BOLD", false);
        boolean isItalic = prefs.getBoolean(PREF_PREFIX_KEY + appWidgetId +"_ITALIC", false);
        int textsize = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", 20);
        int textcolor = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_TextColor", Color.BLACK);
        Sticker sticker = new Sticker(title, isBold, isItalic, textsize, textcolor);
        return sticker;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_BOLD");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_ITALIC");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_TextSize");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_TextColor");
        prefs.apply();
    }
}
