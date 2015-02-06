package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.kaidi.fordrinking.R;

import java.util.ArrayList;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPreviewAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap[] mBitmaps;
    private Helper   mHelper;
    private ArrayList<String> mData;
    private ArrayList<String> selectedURLs;
    private ArrayList<View> mHolders;

    private int index = -1;


    public PhotoPreviewAdapter(Context c, ArrayList<String> data) {
        mContext = c;
        mData    = data;
        mBitmaps = new Bitmap[data.size()];
        mHelper  = new Helper(c);
        mHolders = new ArrayList<View>();
        selectedURLs = new ArrayList<String>();
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
        Holder holder;
        if (position != index && position > index) { // if it's not recycled, initialize some attributes
            index = position;
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photopreview_item, null);
            holder = new Holder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.photo_preview_image);
            convertView.setTag(holder);
            mHolders.add(convertView);
        } else {
            holder = (Holder) mHolders.get(position).getTag();
            convertView = mHolders.get(position);
        }

        if (mBitmaps[position] == null) {
            mHelper.loadImage(holder.imageView, new LoadImageCallBack(position), mData.get(position));
        }
        else
        {
            holder.imageView.setImageBitmap(mBitmaps[position]);
        }

        return convertView;
    }

    private class Holder{
        public ImageView imageView;
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
