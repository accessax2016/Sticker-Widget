package com.example.thanhtung.stickerwidget;

/**
 * Created by Thanh Tung on 6/3/2017.
 */

public class Sticker {
    String title;
    boolean isBold;
    boolean isItalic;
    int textsize;
    int color;

    public Sticker(String title, boolean isBold, boolean isItalic, int textsize, int color) {
        this.title = title;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.textsize = textsize;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    public int getTextsize() {
        return textsize;
    }

    public void setTextsize(int textsize) {
        this.textsize = textsize;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
