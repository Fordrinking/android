package com.kaidi.fordrinking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by kaidi on 15-2-2.
 */
public class AddActivity extends Activity {
    private ImageButton newBlogBtn;
    private ImageButton newPhotoBtn;
    private ImageButton newSoundBtn;
    private ImageButton newVideoBtn;
    private ImageButton newMessageBtn;
    private ImageButton newPollBtn;
    private ImageButton quitAddBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newBlogBtn = (ImageButton)findViewById(R.id.new_blog_btn);
        newPhotoBtn = (ImageButton)findViewById(R.id.new_photo_btn);
        newSoundBtn = (ImageButton)findViewById(R.id.new_sound_btn);
        newVideoBtn = (ImageButton)findViewById(R.id.new_video_btn);
        newMessageBtn = (ImageButton)findViewById(R.id.new_message_btn);
        newPollBtn = (ImageButton)findViewById(R.id.new_poll_btn);
        quitAddBtn = (ImageButton)findViewById(R.id.quit_add_btn);

        newBlogBtn.setOnClickListener(new AddNewContentListener());
        newPhotoBtn.setOnClickListener(new AddNewContentListener());
        newSoundBtn.setOnClickListener(new AddNewContentListener());
        newMessageBtn.setOnClickListener(new AddNewContentListener());
        newPollBtn.setOnClickListener(new AddNewContentListener());
        newVideoBtn.setOnClickListener(new AddNewContentListener());

        quitAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private class AddNewContentListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(AddActivity.this, AddContentActivity.class);
            intent.putExtra("viewID", view.getId());
            startActivity(intent);
            finish();
        }
    }
}