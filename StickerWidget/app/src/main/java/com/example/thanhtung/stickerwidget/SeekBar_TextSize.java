package com.example.thanhtung.stickerwidget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import static com.example.thanhtung.stickerwidget.StickerWidgetConfigureActivity.edtSticker;
import static com.example.thanhtung.stickerwidget.StickerWidgetConfigureActivity.mTextSize;

/**
 * Created by Thanh Tung on 6/3/2017.
 */

public class SeekBar_TextSize extends Fragment {
    SeekBar seekBar;
    CheckBox chkAuto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_seekbar, container, false);

        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        chkAuto = (CheckBox) v.findViewById(R.id.chkAuto);
        chkAuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                seekBar.setProgress(20);
                edtSticker.setTextSize(20);
                seekBar.setEnabled(false);
            }
        });
        seekBar.setProgress(20);
        seekBar.setMax(80);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });

        return v;
    }
}
