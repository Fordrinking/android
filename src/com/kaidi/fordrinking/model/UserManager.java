package com.kaidi.fordrinking.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.kaidi.fordrinking.R;
import com.kaidi.fordrinking.util.DBUtil;

import java.util.ArrayList;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class UserManager {

    public static ArrayList<User> users = new ArrayList<User>();

    public static void addUser(User user) {
        DBUtil.insertUser(user);
    }

    public static void removeUser(User user) {
        DBUtil.deleteUser(user);
    }

    public static User getUser(int uid) {
        Cursor cursor = DBUtil.queryUser(uid);
        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndex("username"));
        String email    = cursor.getString(cursor.getColumnIndex("email"));
        String password = cursor.getString(cursor.getColumnIndex("password"));
        String avatar   = cursor.getString(cursor.getColumnIndex("avatar"));
        return new User(uid, username, email, password, avatar);
    }

    public static User getCurrentUser(Context ctx) {
        String shareFileName = ctx.getResources().getString(R.string.share_preference_file);
        SharedPreferences sharedPref = ctx.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

        int uid = sharedPref.getInt(ctx.getResources().getString(R.string.share_pref_curr_uid), -1);
        if (uid == -1) {
            return null;
        } else {
            return getUser(uid);
        }
    }

    public static void switchToCurrent(Context ctx, int uid) {
        String shareFileName = ctx.getResources().getString(R.string.share_preference_file);
        SharedPreferences sharedPref = ctx.getSharedPreferences(shareFileName, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ctx.getResources().getString(R.string.share_pref_curr_uid), uid);
        editor.apply();
    }
}















