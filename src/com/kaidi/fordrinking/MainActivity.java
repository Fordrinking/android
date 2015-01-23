package com.kaidi.fordrinking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kaidi.fordrinking.drawer.InitDrawer;
import com.kaidi.fordrinking.util.Misc;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends ActionBarActivity {




    private DrawerLayout mDrawerLayout;



    private Toolbar mActionBarToolbar;
    private String appName;
    private String toolbarTitle;
    private ActionBarDrawerToggle mDrawerToggle;

    private HttpClient httpClient;

    Handler handler = new Handler() {
        public void handlerMessage(Message msg) {
            if (msg.what == 0x123) {

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Resources resources = getResources();

        appName = resources.getString(R.string.app_name);

        toolbarTitle = resources.getString(R.string.toolbar_title);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        httpClient = new DefaultHttpClient();

        //initialize Toolbar
        getActionBarToolbar();

        //once the toolbar has been set as the action bar (see setSupportActionBar in
        // getActionBarToolbar() method), use getSupportActionBar to work with the toolbar
        // e.g setTitle, setDisplayShowTitleEnabled
        getSupportActionBar().setTitle(toolbarTitle);

        mActionBarToolbar.setTitleTextAppearance(getApplicationContext(), R.style.ToolbarTitle);

        setupNavDrawer();

        InitDrawer.getInitDrawer(this);

    }
    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }

        return mActionBarToolbar;
    }

    private void setupNavDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color
                .theme_primary_dark));

        if (mActionBarToolbar != null) {
            mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                        mDrawerLayout.closeDrawer(Gravity.START);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.START);
                    }
                }
            });
        }

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
                //mActionBarToolbar.setTitle(getResources().getString(R.string.drawer_open));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                mActionBarToolbar.setTitle("For Drinking");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mActionBarToolbar.setTitle("Explore");
    }

    private void setupUserAuth() {

    }
/*
    public void accept() {
        new Thread() {
            @Override
            public void run() {
                HttpGet httpGet = new HttpGet("http://fordrinking.com:8888/login");
                try {
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    HttpEntity entity = httpResponse.getEntity();
                    if (entity != null) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            Message msg = new Message();
                            msg.what = 0x123;
                            msg.obj = line;
                            handler.sendMessage(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void showLogin(View v) {
        final View loginDialog = getLayoutInflater().inflate(R.layout.login, null);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Login")
                .setView(loginDialog)
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String name = ((EditText) loginDialog
                                .findViewById(R.id.name)).getText()
                                .toString();
                        final String pass = ((EditText) loginDialog
                                .findViewById(R.id.pass)).getText()
                                .toString();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    HttpPost httpPost = new HttpPost("http://fordrinking.com:8888/sign-up");
                                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                                    params.add(new BasicNameValuePair("name", name));
                                    params.add(new BasicNameValuePair("pass", pass));
                                    httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                                    HttpResponse httpResponse = httpClient.execute(httpPost);
                                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                        String msg = EntityUtils.toString(httpResponse.getEntity());
                                        Looper.prepare();
                                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        Looper.loop();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }).setNegativeButton("Cancel", null).show();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
    }

    public Toolbar getmActionBarToolbar() {
        return mActionBarToolbar;
    }

    public void setmActionBarToolbar(Toolbar mActionBarToolbar) {
        this.mActionBarToolbar = mActionBarToolbar;
    }
}