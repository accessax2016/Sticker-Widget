package com.example.thanhtung.stickerwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import static com.example.thanhtung.stickerwidget.R.drawable.memo_tag_001;

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
    int mBackground = R.drawable.memo_bg_001;
    int mTag = memo_tag_001;
    int mIcon = R.drawable.y_girlemo001;

    RelativeLayout rlSticker;
    ImageView imgBackground;
    ImageView imgTag;
    ImageView imgIcon;
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

//        Sticker sticker = WidgetPrefs.loadPref(StickerWidgetConfigureActivity.this, mAppWidgetId);
//        edtSticker.setText(sticker.getTitle());
//        edtSticker.setTypeface(null, sticker.getStyle());
//        edtSticker.setTextSize(sticker.getTextsize());
//        edtSticker.setTextColor(sticker.getColor());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isSave)
            loOK.performClick();
        else {
            // Make sure we pass back the original appWidgetId
            Intent resultValueCancel = new Intent();
            resultValueCancel.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_CANCELED, resultValueCancel);
            finish();
        }
    }

    private void initControl() {
        rlSticker = (RelativeLayout) findViewById(R.id.rlSticker);
        imgBackground = (ImageView) findViewById(R.id.imgBackground);
        imgTag = (ImageView) findViewById(R.id.imgTag);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
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
                WidgetPrefs.savePref(context, mAppWidgetId, edtSticker.getText().toString(), isBold, isItalic, mTextSize, mTextColor,
                        (Integer)imgBackground.getTag(), (Integer)imgTag.getTag(), (Integer)imgIcon.getTag());

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
                Sticker sticker = WidgetPrefs.loadPref(context, mAppWidgetId);
                //views.setTextViewText(R.id.appwidget_text, sticker.getTitle());
                views.setTextViewText(R.id.appwidget_text, Html.fromHtml(addItalicTag(sticker.isItalic(), addBoldTag(sticker.isBold(), replaceNewLine(sticker.getTitle())))));
                views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
                views.setTextColor(R.id.appwidget_text, sticker.getColor());
                views.setImageViewResource(R.id.appwidget_background, sticker.getBackground());
                views.setImageViewResource(R.id.appwidget_tag, sticker.getTag());
                views.setImageViewResource(R.id.appwidget_icon, sticker.getIcon());

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
                else {
                    // Make sure we pass back the original appWidgetId
                    Intent resultValueCancel = new Intent();
                    resultValueCancel.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_CANCELED, resultValueCancel);
                    finish();
                }
                break;
            case R.id.loSave:
                isSave = true;
                break;
            case R.id.loClear:
                edtSticker.setText("");
                edtSticker.setTypeface(null, Typeface.NORMAL);
                loBold.setSelected(false);
                loItalic.setSelected(false);
                edtSticker.setTextSize(20);
                edtSticker.setTextColor(Color.BLACK);
                imgBackground.setImageResource(R.drawable.memo_bg_001);
                imgBackground.setTag(R.drawable.memo_bg_001);
                imgTag.setImageResource(memo_tag_001);
                imgTag.setTag(memo_tag_001);
                imgIcon.setImageResource(R.drawable.y_girlemo001);
                imgIcon.setTag(R.drawable.y_girlemo001);
                //
                isSave = false;
                isBold = false;
                isItalic = false;
                mTextSize = 20;
                mTextColor = Color.BLACK;
                mBackground = R.drawable.memo_bg_001;
                mTag = memo_tag_001;
                mIcon = R.drawable.y_girlemo001;
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
                PopupMenuSkin(loSkin);
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
    public void PopupMenuSkin(View v) {
        final int[] listBackground = { R.drawable.memo_bg_001, R.drawable.memo_bg_002, R.drawable.memo_bg_003, R.drawable.memo_bg_004, R.drawable.memo_bg_005,
                                R.drawable.memo_bg_006, R.drawable.memo_bg_007, R.drawable.memo_bg_008, R.drawable.memo_bg_009, R.drawable.memo_bg_010,
                                R.drawable.memo_bg_011, R.drawable.memo_bg_012, R.drawable.memo_bg_013, R.drawable.memo_bg_014, R.drawable.memo_bg_015,
                                R.drawable.memo_bg_016, R.drawable.memo_bg_017, R.drawable.memo_bg_018, R.drawable.memo_bg_019, R.drawable.memo_bg_020,
                                R.drawable.memo_bg_021, R.drawable.memo_bg_022, R.drawable.memo_bg_023, R.drawable.memo_bg_024, R.drawable.memo_bg_025,
                                R.drawable.memo_bg_026, R.drawable.memo_bg_027, R.drawable.memo_bg_028, R.drawable.memo_bg_029, R.drawable.memo_bg_030,
                                R.drawable.memo_bg_031, R.drawable.memo_bg_032, R.drawable.memo_bg_033, R.drawable.memo_bg_034, R.drawable.memo_bg_035 };
        final int[] listTag = { memo_tag_001, R.drawable.memo_tag_002, R.drawable.memo_tag_003, R.drawable.memo_tag_004, R.drawable.memo_tag_005,
                        R.drawable.memo_tag_006, R.drawable.memo_tag_007, R.drawable.memo_tag_008, R.drawable.memo_tag_009, R.drawable.memo_tag_010,
                        R.drawable.memo_tag_011, R.drawable.memo_tag_012, R.drawable.memo_tag_013, R.drawable.memo_tag_014, R.drawable.memo_tag_015,
                        R.drawable.memo_tag_016, R.drawable.memo_tag_017, R.drawable.memo_tag_018, R.drawable.memo_tag_019, R.drawable.memo_tag_020,
                        R.drawable.memo_tag_021, R.drawable.memo_tag_022, R.drawable.memo_tag_023, R.drawable.memo_tag_024, R.drawable.memo_tag_025,
                        R.drawable.memo_tag_026, R.drawable.memo_tag_027, R.drawable.memo_tag_028, R.drawable.memo_tag_029, R.drawable.memo_tag_030,
                        R.drawable.memo_tag_031, R.drawable.memo_tag_032, R.drawable.memo_tag_033, R.drawable.memo_tag_034, R.drawable.memo_tag_035 };
        final int[] listIcon = { R.drawable.y_girlemo001, R.drawable.y_girlemo002, R.drawable.y_girlemo003, R.drawable.y_girlemo004, R.drawable.y_girlemo005,
                        R.drawable.y_girlemo006, R.drawable.y_girlemo007, R.drawable.y_girlemo008, R.drawable.y_girlemo009, R.drawable.y_girlemo010,
                        R.drawable.y_girlemo011, R.drawable.y_girlemo012, R.drawable.y_girlemo013, R.drawable.y_girlemo014, R.drawable.y_girlemo015,
                        R.drawable.y_girlemo016, R.drawable.y_girlemo017, R.drawable.y_girlemo018, R.drawable.y_girlemo019, R.drawable.y_girlemo020,
                        R.drawable.y_girlemo021, R.drawable.y_girlemo022, R.drawable.y_girlemo023, R.drawable.y_girlemo024, R.drawable.y_girlemo025,
                        R.drawable.y_girlemo026, R.drawable.y_girlemo027, R.drawable.y_girlemo028, R.drawable.y_girlemo029, R.drawable.y_girlemo030,
                        R.drawable.y_girlemo031, R.drawable.y_girlemo032, R.drawable.y_girlemo033, R.drawable.y_girlemo034, R.drawable.y_girlemo035,
                        R.drawable.y_girlemo036, R.drawable.y_girlemo037, R.drawable.y_girlemo038, R.drawable.y_girlemo039, R.drawable.y_girlemo040,
                        R.drawable.y_girlemo041, R.drawable.y_girlemo042, R.drawable.y_girlemo043, R.drawable.y_girlemo044, R.drawable.y_girlemo045,
                        R.drawable.y_girlemo046, R.drawable.y_girlemo047, R.drawable.y_girlemo048, R.drawable.y_girlemo049, R.drawable.y_girlemo050, };

        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.getMenu().add(1, 1, 1, "Background");
        popupMenu.getMenu().add(1, 2, 1, "Tag");
        popupMenu.getMenu().add(1, 3, 1, "Icon");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        showAlertDialog(listBackground, item.getItemId(), 2, imgBackground, item.getTitle().toString());
                        break;
                    case 2:
                        showAlertDialog(listTag, item.getItemId(),  3, imgTag, item.getTitle().toString());
                        break;
                    case 3:
                        showAlertDialog(listIcon, item.getItemId(), 3, imgIcon, item.getTitle().toString());
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();

    }

    private void showAlertDialog(int[] listItemID, int type, int numberColumn, final ImageView imageViewChange, String title) {
        GridView gridView = new GridView(this);
        gridView.setAdapter(new SkinAdapter(this, listItemID, type));
        gridView.setNumColumns(numberColumn);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int img = (Integer) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), String.valueOf(img), Toast.LENGTH_SHORT).show();
                imageViewChange.setImageResource(img);
                imageViewChange.setTag(img);
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle("Choose "+title);
        builder.show();
    }
}

