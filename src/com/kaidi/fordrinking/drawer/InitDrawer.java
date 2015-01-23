package com.kaidi.fordrinking.drawer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
        InitDrawerAction();
        InitControls();
    }

    public static synchronized InitDrawer getInitDrawer(MainActivity activity){
        if(initDrawer == null){
            initDrawer = new InitDrawer(activity);
        }
        return initDrawer;
    }
    
    private void InitDrawerAction() {

    }

    private void InitControls() {
        final Toolbar toolbar = activity.getmActionBarToolbar();
        LinearLayout loginItem = (LinearLayout) activity.findViewById(R.id.auth_login_item);
        loginItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.findViewById(R.id.page_auth_login).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.page_auth_signup).setVisibility(View.GONE);
                activity.findViewById(R.id.page_explore).setVisibility(View.GONE);
                activity.getmDrawerLayout().closeDrawer(Gravity.START);
                toolbar.setTitle("Log in");
            }
        });

        LinearLayout signupItem = (LinearLayout) activity.findViewById(R.id.auth_signup_item);
        signupItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.findViewById(R.id.page_auth_login).setVisibility(View.GONE);
                activity.findViewById(R.id.page_auth_signup).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.page_explore).setVisibility(View.GONE);
                activity.getmDrawerLayout().closeDrawer(Gravity.START);
                toolbar.setTitle("Sign up");
            }
        });

        LinearLayout exploreItem = (LinearLayout) activity.findViewById(R.id.auth_explore_item);
        exploreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.findViewById(R.id.page_auth_login).setVisibility(View.GONE);
                activity.findViewById(R.id.page_auth_signup).setVisibility(View.GONE);
                activity.findViewById(R.id.page_explore).setVisibility(View.VISIBLE);
                activity.getmDrawerLayout().closeDrawer(Gravity.START);
                toolbar.setTitle("Explore");
            }
        });

    }
}








