package com.example.thanhtung.stickerwidget;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * The configuration screen for the {@link StickerWidget StickerWidget} AppWidget.
 */
public class StickerWidgetConfigureActivity extends Activity implements View.OnClickListener{

    private static final String PREFS_NAME = "com.example.thanhtung.stickerwidget.StickerWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    int mTypeface;
    public  static int mTextSize;

    TextView tvCharactersLeft;
    RelativeLayout rlSticker;
    public static EditText edtSticker;
    LinearLayout loSkin;
    LinearLayout loOK;
    LinearLayout loCancel;
    LinearLayout loSave;
    LinearLayout loClear;
    LinearLayout loColor;
    LinearLayout loTextSize;
    LinearLayout loBold;
    LinearLayout loItalic;

    public StickerWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void savePref(Context context, int appWidgetId, String title, int style, int textsize) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, title);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_Style", style);
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", textsize);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static Sticker loadPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String title = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        int style = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_Style", Typeface.NORMAL);
        int textsize = prefs.getInt(PREF_PREFIX_KEY + appWidgetId +"_TextSize", 20);
        Sticker sticker = new Sticker(title, style, textsize);
        return sticker;
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.sticker_widget_configure);

        initControl();
        setEvent();

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        Sticker sticker = loadPref(StickerWidgetConfigureActivity.this, mAppWidgetId);
        edtSticker.setText(sticker.getTitle());
        edtSticker.setTypeface(null, sticker.getStyle());
        edtSticker.setTextSize(sticker.getTextsize());
    }

    private void initControl() {
        tvCharactersLeft = (TextView) findViewById(R.id.tvCharactersLeft);
        rlSticker = (RelativeLayout) findViewById(R.id.rlSticker);
        edtSticker = (EditText) findViewById(R.id.edtSticker);
        loSkin = (LinearLayout) findViewById(R.id.loSkin);
        loOK = (LinearLayout) findViewById(R.id.loOK);
        loCancel = (LinearLayout) findViewById(R.id.loCancel);
        loSave = (LinearLayout) findViewById(R.id.loSave);
        loClear = (LinearLayout) findViewById(R.id.loClear);
        loColor = (LinearLayout) findViewById(R.id.loColor);
        loTextSize = (LinearLayout) findViewById(R.id.loTextSize);
        loBold = (LinearLayout) findViewById(R.id.loBold);
        loItalic = (LinearLayout) findViewById(R.id.loItalic);
    }

    private void setEvent() {
        tvCharactersLeft.setOnClickListener(this);
        rlSticker.setOnClickListener(this);
        edtSticker.setOnClickListener(this);
        loSkin.setOnClickListener(this);
        loOK.setOnClickListener(this);
        loCancel.setOnClickListener(this);
        loSave.setOnClickListener(this);
        loClear.setOnClickListener(this);
        loColor.setOnClickListener(this);
        loTextSize.setOnClickListener(this);
        loBold.setOnClickListener(this);
        loItalic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCharactersLeft:
                break;
            case R.id.rlSticker:
                break;
            case R.id.edtSticker:
                break;
            case R.id.loSkin:
                break;
            case R.id.loOK:
                final Context context = StickerWidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                savePref(context, mAppWidgetId, edtSticker.getText().toString(), mTypeface, mTextSize);

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                StickerWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
                break;
            case R.id.loCancel:
                break;
            case R.id.loSave:
                break;
            case R.id.loClear:
                break;
            case R.id.loColor:
                break;
            case R.id.loTextSize:
                loadFragment(new SeekBar_TextSize());
                break;
            case R.id.loBold:
                if (loBold.isSelected()) {
                    if (loItalic.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.ITALIC);
                        mTypeface = Typeface.ITALIC;
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.NORMAL);
                        mTypeface = Typeface.NORMAL;
                    }
                    loBold.setSelected(false);
                }
                else {
                    if (loItalic.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
                        mTypeface = Typeface.BOLD_ITALIC;
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.BOLD);
                        mTypeface = Typeface.BOLD;
                    }
                    loBold.setSelected(true);
                }
                break;
            case R.id.loItalic:
                if (loItalic.isSelected()) {
                    if (loBold.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD);
                        mTypeface = Typeface.BOLD;
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.NORMAL);
                        mTypeface = Typeface.NORMAL;
                    }
                    loItalic.setSelected(false);
                }
                else {
                    if (loBold.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
                        mTypeface = Typeface.BOLD_ITALIC;
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.ITALIC);
                        mTypeface = Typeface.ITALIC;
                    }
                    loItalic.setSelected(true);
                }
                break;
            default:
                break;
        }
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fmDetailStyle, fragment);
        fragmentTransaction.commit();
    }
}

