package com.example.thanhtung.stickerwidget;

/**
 * Created by Thanh Tung on 6/3/2017.
 */

public class Sticker {
    String title;
    int style;
    int textsize;

    public Sticker(String title, int style, int textsize) {
        this.title = title;
        this.style = style;
        this.textsize = textsize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getTextsize() {
        return textsize;
    }

    public void setTextsize(int textsize) {
        this.textsize = textsize;
    }
}
