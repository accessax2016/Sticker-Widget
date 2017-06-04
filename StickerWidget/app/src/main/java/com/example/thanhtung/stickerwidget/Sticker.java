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
    int background;
    int tag;
    int icon;

    public Sticker(String title, boolean isBold, boolean isItalic, int textsize, int color, int background, int tag, int icon) {
        this.title = title;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.textsize = textsize;
        this.color = color;
        this.background = background;
        this.tag = tag;
        this.icon = icon;
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

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
