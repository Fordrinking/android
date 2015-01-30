package com.kaidi.fordrinking.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.kaidi.fordrinking.model.User;

import static com.kaidi.fordrinking.util.Constants.CREATE_USER_TABLE;

/**
 * Author: kaidi
 * Date: 01/30, 2015
 */
public class DBUtil {


    private static SQLiteDatabase db = null;

    public static void InitDB(Context ctx) {
        db = SQLiteDatabase.openOrCreateDatabase(ctx.getFilesDir().toString() + "/fd.db", null);
        db.execSQL(CREATE_USER_TABLE);
    }

    public static SQLiteDatabase getDB() {
        return db;
    }

    public static void insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("uid", user.getUid());
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("avatar", user.getAvatar());
        db.insert("fd_users", null, contentValues);
    }

    public static void deleteUser(User user) {
        String uid = String.valueOf(user.getUid());
        db.delete("fd_users", "uid = ? ", new String[] {uid});
    }

    public static Cursor queryUser(int uid) {
        String uidStr = String.valueOf(uid);
        return db.query("fd_users",
                        null,                  //columns
                        "uid = ?",             //selections
                        new String[]{uidStr},  //selectionArgs
                        null,                  //group by
                        null,                  //having
                        null,                  //order by
                        null);                 //limit
    }
}
