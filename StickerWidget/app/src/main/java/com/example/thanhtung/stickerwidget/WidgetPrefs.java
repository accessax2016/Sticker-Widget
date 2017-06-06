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
    static void savePref(Context context, int appWidgetId, String title, boolean isBold, boolean isItalic, int textsize, int color,
                         int background, int tag, int icon) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, title);
        prefs.putBoolean(PREF_PREFIX_KEY + appWidgetId +"_BOLD", isBold);
        prefs.putBoolean(PREF_PREFIX_KEY + appWidgetId +"_ITALIC", isItalic);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", textsize);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_TextColor", color);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_Background", background);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_Tag", tag);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_Icon", icon);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static Sticker loadPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String title = prefs.getString(PREF_PREFIX_KEY + appWidgetId, "Hello");
        boolean isBold = prefs.getBoolean(PREF_PREFIX_KEY + appWidgetId +"_BOLD", false);
        boolean isItalic = prefs.getBoolean(PREF_PREFIX_KEY + appWidgetId +"_ITALIC", false);
        int textsize = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", 20);
        int textcolor = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_TextColor", Color.BLACK);
        int background = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_Background", R.drawable.memo_bg_001);
        int tag = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_Tag", R.drawable.memo_tag_001);
        int icon = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_Icon", R.drawable.y_girlemo001);
        Sticker sticker = new Sticker(title, isBold, isItalic, textsize, textcolor, background, tag, icon);
        return sticker;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_BOLD");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_ITALIC");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_TextSize");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_TextColor");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_Background");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_Tag");
        prefs.remove(PREF_PREFIX_KEY + appWidgetId +"_Icon");
        prefs.apply();
    }
}
