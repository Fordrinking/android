package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.kaidi.fordrinking.view.SquareImageView;

import java.util.ArrayList;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPickerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData;
    private Bitmap[] mBitmaps;
    private Helper   mHelper;

    public PhotoPickerAdapter(Context c, ArrayList<String> data) {
        mContext = c;
        mData    = data;
        mBitmaps = new Bitmap[data.size()];
        mHelper  = new Helper(c);
    }

    public int getCount() {
        return mData.size();
    }

    public Object getItem(int position) {
        return mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            imageView.setBackgroundColor(Color.DKGRAY);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (SquareImageView) convertView;
        }

        if (mBitmaps[position] == null) {
            mHelper.loadImage(imageView, new LoadImageCallBack(position), mData.get(position));
        }
        else
        {
            imageView.setImageBitmap(mBitmaps[position]);
        }

        return imageView;
    }

    private class LoadImageCallBack implements ImageCallback {
        int index;
        public LoadImageCallBack(int i) {
            this.index = i;
        }

        @Override
        public void run(ImageView imageView, Bitmap bitmap) {
            mBitmaps[index] = bitmap;
            imageView.setImageBitmap(bitmap);
        }
    }
}
