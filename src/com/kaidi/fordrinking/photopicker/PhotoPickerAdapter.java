package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.kaidi.fordrinking.R;

import java.util.ArrayList;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPickerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData;
    private Bitmap[] mBitmaps;
    private Helper   mHelper;
    private int selection = -1;


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

    public void setSeclection(int position) {
        selection = position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder layout;
        SquareImageView imageView;
       // Log.e("photo_picker", "" + position);
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            Log.e("photo_picker", "" + position);
            imageView = new SquareImageView(mContext);
            imageView.setBackgroundColor(Color.CYAN);
            imageView.setImageResource(R.drawable.ic_insert_photo_white_48dp);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layout = new ImageHolder(mContext);

            layout.addView(imageView);
        } else {
            layout = (ImageHolder)convertView;
            imageView = (SquareImageView) layout.getChildAt(0);
        }

        if (mBitmaps[position] == null) {
            Log.e("photo_picker_setimage", "null-pos" + position);
            mHelper.loadImage(imageView, new LoadImageCallBack(position), mData.get(position));
        }
        else
        {
            Log.e("photo_picker_setimage", "pos" + position);
            imageView.setImageBitmap(mBitmaps[position]);
        }

        if (layout.getCheckedState() == 1) {
           // Log.e("photo_picker_checked", "" + position);
            layout.setChecked();
        } else {
          //  Log.e("photo_picker_un_checked", "" + position);
            layout.setUnChecked();
        }

        return layout;
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
