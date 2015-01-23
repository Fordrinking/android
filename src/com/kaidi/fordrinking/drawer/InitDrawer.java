package com.kaidi.fordrinking.drawer;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;

/**
 * Author: kaidi
 * Date: 01/23, 2015
 */
public class InitDrawer extends BasicDrawer {
    private MainActivity activity;

    private static InitDrawer initDrawer = null;

    private InitDrawer(MainActivity activity) {
        this.activity = activity;
        InitControls();
    }

    public static synchronized InitDrawer getInitDrawer(MainActivity activity){
        if(initDrawer == null){
            initDrawer = new InitDrawer(activity);
        }
        return initDrawer;
    }

    private void InitControls() {
        TextView loginTextView = (TextView) activity.findViewById(R.id.signup_text_view);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activity.getmDrawerLayout().closeDrawer(Gravity.START);
            }
        });
















    }
}
