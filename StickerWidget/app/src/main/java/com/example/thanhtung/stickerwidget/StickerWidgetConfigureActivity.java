package com.example.thanhtung.stickerwidget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

/**
 * The configuration screen for the {@link StickerWidget StickerWidget} AppWidget.
 */
public class StickerWidgetConfigureActivity extends Activity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    Boolean isSave;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Boolean isBold = false;
    Boolean isItalic = false;
    int mTextSize = 20;
    int mTextColor = Color.BLACK;

    RelativeLayout rlSticker;
    EditText edtSticker;
    LinearLayout loOK;
    LinearLayout loCancel;
    LinearLayout loSave;
    LinearLayout loClear;
    SeekBar seekBar;
    LinearLayout loColor;
    LinearLayout loSkin;
    LinearLayout loBold;
    LinearLayout loItalic;

    public StickerWidgetConfigureActivity() {
        super();
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

        Sticker sticker = WidgetPrefs.loadPref(StickerWidgetConfigureActivity.this, mAppWidgetId);
        edtSticker.setText(sticker.getTitle());
        //edtSticker.setTypeface(null, sticker.getStyle());
        edtSticker.setTextSize(sticker.getTextsize());
        edtSticker.setTextColor(sticker.getColor());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isSave)
            loOK.performClick();
        finish();
    }

    private void initControl() {
        rlSticker = (RelativeLayout) findViewById(R.id.rlSticker);
        edtSticker = (EditText) findViewById(R.id.edtSticker);
        loOK = (LinearLayout) findViewById(R.id.loOK);
        loCancel = (LinearLayout) findViewById(R.id.loCancel);
        loSave = (LinearLayout) findViewById(R.id.loSave);
        loClear = (LinearLayout) findViewById(R.id.loClear);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        loColor = (LinearLayout) findViewById(R.id.loColor);
        loSkin = (LinearLayout) findViewById(R.id.loSkin);
        loBold = (LinearLayout) findViewById(R.id.loBold);
        loItalic = (LinearLayout) findViewById(R.id.loItalic);
    }

    private void setEvent() {
        rlSticker.setOnClickListener(this);
        edtSticker.setOnClickListener(this);
        loOK.setOnClickListener(this);
        loCancel.setOnClickListener(this);
        loSave.setOnClickListener(this);
        loClear.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        loColor.setOnClickListener(this);
        loSkin.setOnClickListener(this);
        loBold.setOnClickListener(this);
        loItalic.setOnClickListener(this);
    }

    public static String addBoldTag(boolean doIt, String original) {
        if (doIt) {
            return "<b>" + original + "</b>";
        }
        return original;
    }

    public static String addItalicTag(boolean doIt, String original) {
        if (doIt) {
            return "<i>" + original + "</i>";
        }
        return original;
    }

    public static String replaceNewLine(String original) {
        return original.replace("\n", "<br>");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSticker:
                break;
            case R.id.edtSticker:
                break;
            case R.id.loOK:
                final Context context = StickerWidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                WidgetPrefs.savePref(context, mAppWidgetId, edtSticker.getText().toString(), isBold, isItalic, mTextSize, mTextColor);

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
                Sticker sticker = WidgetPrefs.loadPref(context, mAppWidgetId);
                //views.setTextViewText(R.id.appwidget_text, sticker.getTitle());
                views.setTextViewText(R.id.appwidget_text, Html.fromHtml(addItalicTag(sticker.isItalic(), addBoldTag(sticker.isBold(), replaceNewLine(sticker.getTitle())))));
                views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
                views.setTextColor(R.id.appwidget_text, sticker.getColor());

                appWidgetManager.updateAppWidget(mAppWidgetId, views);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
                break;
            case R.id.loCancel:
                if (isSave)
                    loOK.performClick();
                finish();
                break;
            case R.id.loSave:
                isSave = true;
                break;
            case R.id.loClear:
                edtSticker.setText("");
                edtSticker.setTypeface(null, Typeface.NORMAL);
                edtSticker.setTextSize(20);
                edtSticker.setTextColor(Color.BLACK);
                loBold.setSelected(false);
                loItalic.setSelected(false);
                //
                isSave = false;
                isBold = false;
                isItalic = false;
                mTextSize = 20;
                mTextColor = Color.BLACK;
                seekBar.setProgress(20);
                break;
            case R.id.loColor:
                ColorPickerDialogBuilder
                        .with(this)
                        .setTitle("Choose Color")
                        .initialColor(edtSticker.getCurrentTextColor())
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setPositiveButton("OK", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                edtSticker.setTextColor(selectedColor);
                                mTextColor = selectedColor;
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
                break;
            case R.id.loSkin:
                break;
            case R.id.loBold:
                if (loBold.isSelected()) {
                    if (loItalic.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.ITALIC);
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.NORMAL);
                    }
                    isBold = false;
                    loBold.setSelected(false);
                }
                else {
                    if (loItalic.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.BOLD);
                    }
                    isBold = true;
                    loBold.setSelected(true);
                }
                break;
            case R.id.loItalic:
                if (loItalic.isSelected()) {
                    if (loBold.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD);
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.NORMAL);
                    }
                    isItalic = false;
                    loItalic.setSelected(false);
                }
                else {
                    if (loBold.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.ITALIC);
                    }
                    isItalic = true;
                    loItalic.setSelected(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        edtSticker.setTextSize(progress);
        mTextSize = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

