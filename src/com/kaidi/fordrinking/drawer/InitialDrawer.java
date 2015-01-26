package com.kaidi.fordrinking.drawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import com.kaidi.fordrinking.MainActivity;
import com.kaidi.fordrinking.R;

import static com.kaidi.fordrinking.util.Constants.*;

/**
 * Author: kaidi
 * Date: 01/23, 2015
 */
public class InitialDrawer extends BasicDrawer {
    private MainActivity activity;

    private static InitialDrawer initialDrawer = null;

    private InitialDrawer(MainActivity activity) {
        this.activity = activity;
        InitDrawerAction();
        InitControls();
    }

    public static synchronized InitialDrawer getInitDrawer(MainActivity activity){
        if(initialDrawer == null){
            initialDrawer = new InitialDrawer(activity);
        }
        return initialDrawer;
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
                activity.setmDrawerTab(DRAWER_LOGIN_STATE);
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
                activity.setmDrawerTab(DRAWER_SIGNUP_STATE);
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
                activity.setmDrawerTab(DRAWER_EXPLORE_STATE);
                toolbar.setTitle("Explore");
            }
        });

    }
}








