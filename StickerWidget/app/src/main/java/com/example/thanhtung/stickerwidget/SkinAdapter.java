package com.example.thanhtung.stickerwidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Thanh Tung on 6/4/2017.
 */

public class SkinAdapter extends BaseAdapter {
    Context mContext;
    int[] listImageID;
    int type;

    public SkinAdapter(Context mContext, int[] listImageID, int type) {
        this.mContext = mContext;
        this.listImageID = listImageID;
        this.type = type;
    }

    @Override
    public int getCount() {
        return listImageID.length;
    }

    @Override
    public Object getItem(int position) {
        return listImageID[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View grid;
        switch (type) {
            case 1:
                grid = inflater.inflate(R.layout.gridview_item_background, null);
                break;
            case 2:
                grid = inflater.inflate(R.layout.gridview_item_tag, null);
                break;
            case 3:
                grid = inflater.inflate(R.layout.gridview_item_icon, null);
                break;
            default:
                grid = inflater.inflate(R.layout.gridview_item_background, null);
                break;
        }

        ImageView imageView = (ImageView) grid.findViewById(R.id.imgItem);
        imageView.setImageResource(listImageID[position]);
        imageView.setTag(listImageID[position]);

        return grid;
    }
}
