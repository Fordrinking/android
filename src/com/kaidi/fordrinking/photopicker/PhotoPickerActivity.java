package com.kaidi.fordrinking.photopicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.kaidi.fordrinking.R;

import java.util.ArrayList;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPickerActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);

        Helper helper = new Helper(this);
        ArrayList<String> photoList = helper.getPhotoList();

        GridView gridview = (GridView) findViewById(R.id.photo_picker_grid);
        gridview.setAdapter(new PhotoPickerAdapter(this, photoList));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PhotoPickerActivity.this, "" + i, Toast.LENGTH_SHORT).show();
            }
        });

    }
}