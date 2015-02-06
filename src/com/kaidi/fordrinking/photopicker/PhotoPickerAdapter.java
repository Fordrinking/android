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
public class PhotoPickerAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap[] mBitmaps;
    private Helper   mHelper;
    private ArrayList<String> mData;
    private ArrayList<String> selectedURLs;
    private ArrayList<View> mHolders;
    private ArrayList<Holder> selectHolders;

    private int index=-1;


    public PhotoPickerAdapter(Context c, ArrayList<String> data) {
        mContext = c;
        mData    = data;
        mBitmaps = new Bitmap[data.size()];
        mHelper  = new Helper(c);
        mHolders = new ArrayList<View>();
        selectedURLs = new ArrayList<String>();
        selectHolders = new ArrayList<Holder>();
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

    public ArrayList<String> getSelectedURLs() {
        return selectedURLs;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
       //Log.e("photo_picker", "" + position);
        if (position != index && position > index) { // if it's not recycled, initialize some attributes
            //Log.e("photo_picker", "" + position);
            index = position;
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photopicker_item, null);
            holder = new Holder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.photo_picker_image);
            holder.imageView_bg = (ImageView)convertView.findViewById(R.id.photo_picker_bg);
            holder.image_index = (TextView)convertView.findViewById(R.id.photo_picker_index);
            convertView.setTag(holder);
            mHolders.add(convertView);
        } else {
            holder = (Holder) mHolders.get(position).getTag();
            convertView = mHolders.get(position);
        }

        if (mBitmaps[position] == null) {
            //Log.e("photo_picker_setimage", "null-pos" + position);
            mHelper.loadImage(holder.imageView, new LoadImageCallBack(position), mData.get(position));
        }
        else
        {
            //Log.e("photo_picker_setimage", "pos" + position);
            holder.imageView.setImageBitmap(mBitmaps[position]);
        }
        if (position == 0) {
            convertView.setOnClickListener(new OpenCameraDealer());
        } else {
            convertView.setOnClickListener(new ImageItemClicker(position, holder));
        }

        return convertView;
    }

    private class Holder{
        public ImageView imageView;
        public ImageView imageView_bg;
        public TextView  image_index;
        public int state = 0;
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

    private class OpenCameraDealer implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            mContext. startActivity(intent);
        }
    }

    private class ImageItemClicker implements View.OnClickListener {
        private int position;
        private Holder holder;

        public ImageItemClicker(int pos, Holder holder) {
            this.position = pos;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (holder.state == 0) {
                selectedURLs.add(mData.get(position));
                selectHolders.add(holder);
                holder.imageView_bg.setVisibility(View.VISIBLE);
                holder.image_index.setVisibility(View.VISIBLE);
                holder.image_index.setText(String.valueOf(selectedURLs.size()));
                holder.state = 1;
            } else {
                int currentIndex = selectHolders.indexOf(holder);
                for (int i = 0; i < selectHolders.size(); i++) {
                    if (i > currentIndex) {
                        selectHolders.get(i).image_index.setText(String.valueOf(i));
                    }
                }
                selectedURLs.remove(selectHolders.indexOf(holder));
                selectHolders.remove(selectHolders.indexOf(holder));
                holder.imageView_bg.setVisibility(View.INVISIBLE);
                holder.image_index.setVisibility(View.INVISIBLE);
                holder.image_index.setText("");
                holder.state = 0;
            }
        }
    }
}
