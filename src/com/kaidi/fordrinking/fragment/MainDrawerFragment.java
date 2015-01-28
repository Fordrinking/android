package com.kaidi.fordrinking.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kaidi.fordrinking.R;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class MainDrawerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(
                R.layout.drawer_after, container, false);
    }
}