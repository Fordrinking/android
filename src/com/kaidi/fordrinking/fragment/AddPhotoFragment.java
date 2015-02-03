package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.photopicker.PhotoPickerActivity;

/**
 * Created by kaidi on 15-2-2.
 */
public class AddPhotoFragment extends Fragment implements AddContent {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.frag_add_photo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        startActivity(intent);


    }

    @Override
    public void send() {
        Toast.makeText(getActivity(), "Add Photo", Toast.LENGTH_SHORT).show();
    }
}