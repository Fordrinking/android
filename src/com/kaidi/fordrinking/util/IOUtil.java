package com.kaidi.fordrinking.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class IOUtil {
    public static void read() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();
                FileInputStream fileInputStream = new FileInputStream(
                        sdCardDir.getCanonicalPath() + "");

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write() {

    }
}
