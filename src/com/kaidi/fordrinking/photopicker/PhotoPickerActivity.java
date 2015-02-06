package com.kaidi.fordrinking.photopicker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.kaidi.fordrinking.AddContentActivity;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.util.DataShare;

import java.util.ArrayList;

/**
 * Created by kaidi on 15-2-3.
 */
public class PhotoPickerActivity extends Activity {
    private GridView gridview;
    private PhotoPickerAdapter photoPickerAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);

        ImageButton backBtn = (ImageButton) findViewById(R.id.photo_picker_back_btn);
        TextView    doneBtn = (TextView)    findViewById(R.id.photo_picker_done_btn);

        backBtn.setOnClickListener(new TitleBarBtnClicker());
        doneBtn.setOnClickListener(new TitleBarBtnClicker());

        Helper helper = new Helper(this);
        ArrayList<String> photoList = helper.getPhotoList();

        photoPickerAdapter = new PhotoPickerAdapter(this, photoList);
        gridview = (GridView) findViewById(R.id.photo_picker_grid);
        gridview.setAdapter(photoPickerAdapter);
    }

    private class TitleBarBtnClicker implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.photo_picker_back_btn:
                    Activity addContentActivity =
                            (Activity) DataShare.getInstance().retrieve("AddContentActivity");
                    addContentActivity.finish();
                    finish();
                    break;
                case R.id.photo_picker_done_btn:
                    DataShare.getInstance().save("selectedURLs", photoPickerAdapter.getSelectedURLs());
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}











