package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaidi.fordrinking.R;

import java.awt.font.TextAttribute;
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


    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageHolder layout;
        SquareImageView imageView;
        Holder holder;
       // Log.e("photo_picker", "" + position);
        if (position != index && position > index) {
       // if (convertView == null) {  // if it's not recycled, initialize some attributes
            Log.e("photo_picker", "" + position);
            index = position;
            convertView = LayoutInflater.from(mContext).inflate(R.layout.photopicker_item, null);
            holder = new Holder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.photo_picker_image);
            holder.imageView_bg = (ImageView)convertView.findViewById(R.id.photo_picker_bg);
            holder.image_index = (TextView)convertView.findViewById(R.id.photo_picker_index);
            convertView.setTag(holder);
            mHolders.add(convertView);
            /*imageView = new SquareImageView(mContext);
            imageView.setBackgroundColor(Color.CYAN);
            imageView.setImageResource(R.drawable.ic_insert_photo_white_48dp);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            layout = new ImageHolder(mContext);

            layout.addView(imageView);*/
        } else {
            holder = (Holder) mHolders.get(position).getTag();
            convertView = mHolders.get(position);
            /*layout = (ImageHolder)convertView;
            imageView = (SquareImageView) layout.getChildAt(0);*/
        }



        if (mBitmaps[position] == null) {
            Log.e("photo_picker_setimage", "null-pos" + position);
            mHelper.loadImage(holder.imageView, new LoadImageCallBack(position), mData.get(position));
        }
        else
        {
            Log.e("photo_picker_setimage", "pos" + position);
            holder.imageView.setImageBitmap(mBitmaps[position]);
        }

      /*  if (layout.getCheckedState() == 1) {
           // Log.e("photo_picker_checked", "" + position);
            layout.setChecked();
        } else {
          //  Log.e("photo_picker_un_checked", "" + position);
            layout.setUnChecked();
        }*/
        convertView.setOnClickListener(new ImageItemClicker(holder));
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

    private class ImageItemClicker implements View.OnClickListener {

        int position;
        Holder holder;

        public ImageItemClicker(Holder holder) {
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
                selectedURLs.remove(selectedURLs.indexOf(selectedURLs.get(position)));
                selectHolders.remove(selectHolders.indexOf(holder));
                holder.imageView_bg.setVisibility(View.INVISIBLE);
                holder.image_index.setVisibility(View.INVISIBLE);
                holder.image_index.setText("");
                holder.state = 0;

            }
        }
    }
}
