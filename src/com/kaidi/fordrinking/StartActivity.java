package com.kaidi.fordrinking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import com.kaidi.fordrinking.model.User;
import com.kaidi.fordrinking.model.UserManager;
import com.kaidi.fordrinking.util.DBUtil;

import static com.kaidi.fordrinking.util.Constants.*;

/**
 * Author: kaidi
 * Date: 01/28, 2015
 */
public class StartActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBUtil.OpenOrCreateDb(getApplication());

        String shareFileName         = getResources().getString(R.string.share_preference_file);
        SharedPreferences sharedPref = getSharedPreferences(shareFileName, Context.MODE_PRIVATE);
        boolean isInit               = sharedPref.getBoolean("init-state", false);

        if (isInit) {

        } else {
            DBUtil.InitDB();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("init-state", true);
            editor.apply();
        }

        Intent intent;
        if (UserManager.getCurrentUser(getApplication()) == null) {
            intent = new Intent(this, AuthActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        finish();
    }




}