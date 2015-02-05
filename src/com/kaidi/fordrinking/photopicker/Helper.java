package com.kaidi.fordrinking.photopicker;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ImageView;
import com.kaidi.fordrinking.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by kaidi on 15-2-3.
 */
public class Helper {

    Context context;

    public Helper(Context context) {
        this.context=context;
    }

    public ArrayList<String> getPhotoList() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = intent.getData();
        ArrayList<String> list = new ArrayList<String>();
        String[] proj ={
                MediaStore.Images.Media.DATA
        };
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);//managedQuery(uri, proj, null, null, null);
        while(cursor.moveToNext()){
            String path =cursor.getString(0);
            list.add(new File(path).getAbsolutePath());
        }
        list.add(0, "take_photo");
        return list;
    }

    public Bitmap getImageFromPath(Uri path, int width, int height)
            throws FileNotFoundException {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;

        BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(path),
                null,
                op);

        int wRatio = (int) Math.ceil(op.outWidth / (float) width);
        int hRatio = (int) Math.ceil(op.outHeight / (float) height);

        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(path), null, op);
    }

    public void loadImage(ImageView imageView, ImageCallback callback, String... params){
        LoadBitmapTask loadImageTask =new LoadBitmapTask(imageView, callback);
        loadImageTask.execute(params);
    }

    private class LoadBitmapTask extends AsyncTask<String, Void, Bitmap> {

        ImageView imageView;
        ImageCallback callback;

        public LoadBitmapTask(ImageView iv, ImageCallback cb) {
            imageView = iv;
            callback  = cb;
        }
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                String url = params[0];
                if (url.equals("take_photo")) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.ic_camera_big_bg_36dp);
                } else {
                    bitmap = getImageFromPath(Uri.fromFile(new File(url)), 300, 300);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            //super.onPostExecute(bitmap);
            if (bitmap != null) {
                callback.run(imageView, bitmap);
            }
        }
    }
}
