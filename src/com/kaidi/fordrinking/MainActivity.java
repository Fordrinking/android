package com.kaidi.fordrinking;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.kaidi.fordrinking.fragment.*;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import static com.kaidi.fordrinking.util.Constants.*;
import static com.kaidi.fordrinking.util.Constants.DRAWER_FEEDBACK_STATE;
import static com.kaidi.fordrinking.util.Constants.DRAWER_SETTTINGS_STATE;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class MainActivity extends ActionBarActivity {
    private int drawerTabState;
    private HttpClient httpClient;

    private HomeFragment homeFragment;
    private ExploreFragment exporeFragment;
    private MessageFragment messageFragment;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        registerEvent();
        setDefaultFragment();

        httpClient = new DefaultHttpClient();
    }

    private void initUI() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar      = (Toolbar) findViewById(R.id.toolbar_actionbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("For Drinking");
    }

    private void registerEvent() {
        actionBarDrawerToggle = new DrawerLayoutListener(this,
                drawerLayout, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.main_content, homeFragment);
        transaction.commit();
    }

    private class DrawerLayoutListener extends ActionBarDrawerToggle {

        public DrawerLayoutListener(Activity activity,
                                    DrawerLayout drawerLayout,
                                    int openDrawerContentDescRes,
                                    int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        @Override
        public void onDrawerClosed(View view) {
            supportInvalidateOptionsMenu();
            switch (drawerTabState) {
                case DRAWER_HOME_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_home));
                    break;
                case DRAWER_EXPLORE_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_explore));
                    break;
                case DRAWER_MESSAGE_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_message));
                    break;
                case DRAWER_HELP_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_help));
                    break;
                case DRAWER_SETTTINGS_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_settings));
                    break;
                case DRAWER_FEEDBACK_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_feedback));
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            toolbar.setTitle("For Drinking");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public int getDrawerTabState() {
        return drawerTabState;
    }

    public void setDrawerTabState(int drawerTabState) {
        this.drawerTabState = drawerTabState;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HomeFragment getHomeFragment() {
        return homeFragment;
    }

    public void setHomeFragment(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public ExploreFragment getExporeFragment() {
        return exporeFragment;
    }

    public void setExporeFragment(ExploreFragment exporeFragment) {
        this.exporeFragment = exporeFragment;
    }

    public MessageFragment getMessageFragment() {
        return messageFragment;
    }

    public void setMessageFragment(MessageFragment messageFragment) {
        this.messageFragment = messageFragment;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }
}