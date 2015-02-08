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

/**
 *
 * Author: Kaidi
 * Date: 1/23, 2015
 */
public class AuthActivity extends ActionBarActivity {
    private int drawerTabState;
    private HttpClient httpClient;

    private LoginFragment loginFragment;
    private SignupFragment signupFragment;
    private ExploreFragment exploreFragment;

    private HelpFragment helpFragment;
    private SettingFragment settingFragment;
    private FeedbackFragment feedbackFragment;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

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
        exploreFragment = new ExploreFragment();
        transaction.replace(R.id.auth_content, exploreFragment);
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
                case DRAWER_LOGIN_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_login));
                    break;
                case DRAWER_SIGNUP_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_signup));
                    break;
                case DRAWER_EXPLORE_STATE:
                    toolbar.setTitle(getResources().getString(R.string.drawer_explore));
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

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public LoginFragment getLoginFragment() {
        return loginFragment;
    }

    public void setLoginFragment(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public SignupFragment getSignupFragment() {
        return signupFragment;
    }

    public void setSignupFragment(SignupFragment signupFragment) {
        this.signupFragment = signupFragment;
    }

    public ExploreFragment getExploreFragment() {
        return exploreFragment;
    }

    public void setExploreFragment(ExploreFragment exporeFragment) {
        this.exploreFragment = exporeFragment;
    }

    public HelpFragment getHelpFragment() {
        return helpFragment;
    }

    public void setHelpFragment(HelpFragment helpFragment) {
        this.helpFragment = helpFragment;
    }

    public SettingFragment getSettingFragment() {
        return settingFragment;
    }

    public void setSettingFragment(SettingFragment settingFragment) {
        this.settingFragment = settingFragment;
    }

    public FeedbackFragment getFeedbackFragment() {
        return feedbackFragment;
    }

    public void setFeedbackFragment(FeedbackFragment feedbackFragment) {
        this.feedbackFragment = feedbackFragment;
    }

    public int getDrawerTabState() {
        return drawerTabState;
    }

    public void setDrawerTabState(int drawerTabState) {
        this.drawerTabState = drawerTabState;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


}