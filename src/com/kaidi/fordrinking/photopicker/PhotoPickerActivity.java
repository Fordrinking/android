package com.kaidi.fordrinking.photopicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.kaidi.fordrinking.R;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPickerActivity extends Activity {
    private GridView gridview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);

        Helper helper = new Helper(this);
        ArrayList<String> photoList = helper.getPhotoList();

        final PhotoPickerAdapter photoPickerAdapter = new PhotoPickerAdapter(this, photoList);
        gridview = (GridView) findViewById(R.id.photo_picker_grid);
        gridview.setAdapter(photoPickerAdapter);
    }
}