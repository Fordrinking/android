package com.kaidi.fordrinking.util;

import com.kaidi.fordrinking.model.User;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class JsonUtil {

    public static User parseUser(String msg) {
        User user;
        try {
            JSONObject jsonObject = new JSONObject(msg);
            int uid = jsonObject.getInt("uid");
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String username = jsonObject.getString("username");
            String avatar = jsonObject.getString("avatar");

            user = new User(uid, username, email, avatar);

            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
