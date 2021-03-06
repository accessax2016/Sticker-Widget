package com.example.thanhtung.stickerwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
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

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import static android.graphics.Typeface.BOLD;
import static com.example.thanhtung.stickerwidget.R.drawable.memo_tag_001;

/**
 * The configuration screen for the {@link StickerWidget StickerWidget} AppWidget.
 */
public class StickerWidgetConfigureActivity extends Activity implements View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    static Boolean isSave = false;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;   //khởi tạo id không hợp lệ
    Boolean isBold = false;
    Boolean isItalic = false;
    int mTextSize = 20;
    int mTextColor = Color.BLACK;

    RelativeLayout rlSticker;
    ImageView imgBackground;
    ImageView imgTag;
    ImageView imgIcon;
    EditText edtSticker;
    LinearLayout loOK;
    LinearLayout loCancel;
    LinearLayout loSave;
    ImageView imgSave;
    LinearLayout loClear;
    SeekBar seekBar;
    LinearLayout loColor;
    LinearLayout loSkin;
    LinearLayout loBold;
    ImageView imgBold;
    LinearLayout loItalic;
    ImageView imgItalic;

    public StickerWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setResult(RESULT_CANCELED);     //set Result là RESULT_CANCELED nếu ấn nút Back trên đt để không lưu thao tác

        setContentView(R.layout.sticker_widget_configure);

        initControl();
        setEvent();

        // Tìm id của widget trong Intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // Nếu lấy được id không hợp lệ thì báo lỗi và dừng
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        showView();


    }
    //Hiển thị giao diện
    private void showView() {
        Sticker sticker = WidgetPrefs.loadPref(StickerWidgetConfigureActivity.this, mAppWidgetId);
        edtSticker.setText(sticker.getTitle());
        if (sticker.isBold() && sticker.isItalic()) {
            edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
            isBold = true;
            loBold.setSelected(true);
            imgBold.setImageResource(R.drawable.btn_check);
            isItalic = true;
            loItalic.setSelected(true);
            imgItalic.setImageResource(R.drawable.btn_check);
        }
        else {
            if (sticker.isBold()) {
                edtSticker.setTypeface(null, Typeface.BOLD);
                isBold = true;
                loBold.setSelected(true);
                imgBold.setImageResource(R.drawable.btn_check);
            }

            else {
                if (sticker.isItalic()) {
                    edtSticker.setTypeface(null, Typeface.ITALIC);
                    isItalic = true;
                    loItalic.setSelected(true);
                    imgItalic.setImageResource(R.drawable.btn_check);
                }
                else {
                    edtSticker.setTypeface(null, Typeface.NORMAL);
                    isBold = false;
                    loBold.setSelected(false);
                    imgBold.setImageResource(R.drawable.btn_uncheck);
                    isItalic = false;
                    loItalic.setSelected(false);
                    imgItalic.setImageResource(R.drawable.btn_uncheck);
                }
            }
        }
        edtSticker.setTextSize(sticker.getTextsize());
        seekBar.setProgress(sticker.getTextsize());
        edtSticker.setTextColor(sticker.getColor());
        mTextColor = sticker.getColor();
        imgBackground.setImageResource(sticker.getBackground());
        imgBackground.setTag(sticker.getBackground());
        imgTag.setImageResource(sticker.getTag());
        imgTag.setTag(sticker.getTag());
        imgIcon.setImageResource(sticker.getIcon());
        imgIcon.setTag(sticker.getIcon());
        isSave = false;
        imgSave.setImageResource(R.drawable.btn_uncheck);

    }

    //Ấn nút Back trên đt sẽ kết thúc và không lưu thao tác
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //Khởi tạo các View
    private void initControl() {
        rlSticker = (RelativeLayout) findViewById(R.id.rlSticker);
        imgBackground = (ImageView) findViewById(R.id.imgBackground);
        imgTag = (ImageView) findViewById(R.id.imgTag);
        imgIcon = (ImageView) findViewById(R.id.imgIcon);
        edtSticker = (EditText) findViewById(R.id.edtSticker);
        loOK = (LinearLayout) findViewById(R.id.loOK);
        loCancel = (LinearLayout) findViewById(R.id.loCancel);
        loSave = (LinearLayout) findViewById(R.id.loSave);
        imgSave = (ImageView) findViewById(R.id.imgSave);
        loClear = (LinearLayout) findViewById(R.id.loClear);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        loColor = (LinearLayout) findViewById(R.id.loColor);
        loSkin = (LinearLayout) findViewById(R.id.loSkin);
        loBold = (LinearLayout) findViewById(R.id.loBold);
        imgBold = (ImageView) findViewById(R.id.imgBold);
        loItalic = (LinearLayout) findViewById(R.id.loItalic);
        imgItalic = (ImageView) findViewById(R.id.imgItalic);
    }

    //Gán sự kiện cho các View
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

    //Thêm thẻ <b> cho chuỗi (vì dùng Html)
    public static String addBoldTag(boolean doIt, String original) {
        if (doIt) {
            return "<b>" + original + "</b>";
        }
        return original;
    }

    //Thêm thẻ <i> cho chuỗi (vì dùng Html)
    public static String addItalicTag(boolean doIt, String original) {
        if (doIt) {
            return "<i>" + original + "</i>";
        }
        return original;
    }

    //Sửa /n thành <br> (vì dùng Html)
    public static String replaceNewLine(String original) {
        return original.replace("\n", "<br>");
    }

    //Lưu nội dung vào thẻ nhớ tránh mất dữ liệu quan trọng
    private static void saveSticker(int widgetId, String title) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "StickerWidget");
            if (!root.exists()) {
                root.mkdirs();
            }
            FileWriter writer = new FileWriter(new File(root, new StringBuilder("StickerWidget").append(widgetId).append(".txt").toString()), true);
            Date mDate = new Date();
            writer.append("----------  " + DateFormat.getTimeInstance(2).format(mDate) + ", " + DateFormat.getDateInstance(2).format(mDate) + "  ----------" + "\n" + title + "\n\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            Log.d("saveSticker","Cannot save title");
        }
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, StickerWidget.class));
        for (int widgetId : widgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
            Sticker sticker = WidgetPrefs.loadPref(context, widgetId);
            views.setTextViewText(R.id.appwidget_text, Html.fromHtml(addItalicTag(sticker.isItalic(), addBoldTag(sticker.isBold(), replaceNewLine(sticker.getTitle())))));
            views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
            views.setTextColor(R.id.appwidget_text, sticker.getColor());
            views.setImageViewResource(R.id.appwidget_background, sticker.getBackground());
            views.setImageViewResource(R.id.appwidget_tag, sticker.getTag());
            views.setImageViewResource(R.id.appwidget_icon, sticker.getIcon());
            if (isSave)
                saveSticker(widgetId, sticker.getTitle());

            Intent intent = new Intent(context, StickerWidgetConfigureActivity.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.rlAppWidget, pendingIntent);

            appWidgetManager.updateAppWidget(widgetId, views);
        }

    }

    // Xử lý sự kiện click cho các View
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSticker:
                break;
            case R.id.edtSticker:
                break;
            case R.id.loOK:
                final Context context = StickerWidgetConfigureActivity.this;

                // Khi Click nút OK thì lưu dữ liệu vào SharedPreferences
                WidgetPrefs.savePref(context, mAppWidgetId, edtSticker.getText().toString(), isBold, isItalic, mTextSize, mTextColor,
                        (Integer)imgBackground.getTag(), (Integer)imgTag.getTag(), (Integer)imgIcon.getTag());

                // Cấu hình các thay đổi của widget thông qua AppWidgetManager
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.sticker_widget);
                Sticker sticker = WidgetPrefs.loadPref(context, mAppWidgetId);
                views.setTextViewText(R.id.appwidget_text, Html.fromHtml(addItalicTag(sticker.isItalic(), addBoldTag(sticker.isBold(), replaceNewLine(sticker.getTitle())))));
                views.setTextViewTextSize(R.id.appwidget_text, TypedValue.COMPLEX_UNIT_SP, sticker.getTextsize());
                views.setTextColor(R.id.appwidget_text, sticker.getColor());
                views.setImageViewResource(R.id.appwidget_background, sticker.getBackground());
                views.setImageViewResource(R.id.appwidget_tag, sticker.getTag());
                views.setImageViewResource(R.id.appwidget_icon, sticker.getIcon());
                if (isSave)
                    saveSticker(mAppWidgetId, sticker.getTitle());

                // Trả về Intent gồm id của widget
                Intent resultValue = new Intent(context, StickerWidgetConfigureActivity.class);
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                //resultValue.setData(Uri.withAppendedPath(Uri.parse("Sticker://widget/id/"), String.valueOf(mAppWidgetId)));
                setResult(RESULT_OK, resultValue);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, mAppWidgetId, resultValue, PendingIntent.FLAG_UPDATE_CURRENT);
                // Gán sự kiện khi click widget trên Homesrceen
                views.setOnClickPendingIntent(R.id.rlAppWidget, pendingIntent);
                // Cập nhật các thay đổi
                appWidgetManager.updateAppWidget(mAppWidgetId, views);
                finish();
                break;
            case R.id.loCancel:
                finish();
                break;
            case R.id.loSave:
                if (isSave) {
                    isSave = false;
                    imgSave.setImageResource(R.drawable.btn_uncheck);
                }
                else {
                    isSave = true;
                    imgSave.setImageResource(R.drawable.btn_check);
                }
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
                seekBar.setProgress(20);
                mTextColor = Color.BLACK;
                break;
            case R.id.loColor:
                // Dialog chọn màu cho chữ
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
                    imgBold.setImageResource(R.drawable.btn_uncheck);
                }
                else {
                    if (loItalic.isSelected()) {
                        edtSticker.setTypeface(null, Typeface.BOLD_ITALIC);
                    }
                    else {
                        edtSticker.setTypeface(null, BOLD);
                    }
                    isBold = true;
                    loBold.setSelected(true);
                    imgBold.setImageResource(R.drawable.btn_check);
                }
                break;
            case R.id.loItalic:
                if (loItalic.isSelected()) {
                    if (loBold.isSelected()) {
                        edtSticker.setTypeface(null, BOLD);
                    }
                    else {
                        edtSticker.setTypeface(null, Typeface.NORMAL);
                    }
                    isItalic = false;
                    loItalic.setSelected(false);
                    imgItalic.setImageResource(R.drawable.btn_uncheck);
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
                    imgItalic.setImageResource(R.drawable.btn_check);
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
                //Toast.makeText(getApplicationContext(), String.valueOf(img), Toast.LENGTH_SHORT).show();
                imageViewChange.setImageResource(img);
                imageViewChange.setTag(img);
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(gridView);
        builder.setTitle("Choose "+title);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}

