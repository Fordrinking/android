package com.kaidi.fordrinking.util;

/**
 * Created by kaidi on 15-1-22.
 */
public class Constants {

    public static String HOSTNAME = "http://fordrinking.com";

    public static String GetHostname() {
        return "haha";
    }

    public static final int MORE_BLOG_NUMS         = 5;

    public static final int DRAWER_OPEN_STATE      = 0;
    public static final int DRAWER_LOGIN_STATE     = 1;
    public static final int DRAWER_SIGNUP_STATE    = 2;
    public static final int DRAWER_EXPLORE_STATE   = 3;
    public static final int DRAWER_HELP_STATE      = 4;
    public static final int DRAWER_SETTTINGS_STATE = 5;
    public static final int DRAWER_FEEDBACK_STATE  = 6;
    public static final int DRAWER_HOME_STATE      = 7;
    public static final int DRAWER_MESSAGE_STATE   = 8;

    public static final String SD_FILE_PATH = "/Fordrinking/";

    public static final String CREATE_USER_TABLE =
            "CREATE TABLE IF NOT EXISTS fd_users (" +
                "uid      int(11)      DEFAULT NULL," +
                "username varchar(255) DEFAULT NULL," +
                "password varchar(255) DEFAULT NULL," +
                "email    varchar(255) DEFAULT NULL," +
                "avatar   varchar(255) DEFAULT NULL)";


}
