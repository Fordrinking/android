package com.kaidi.fordrinking.model;

import java.util.ArrayList;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class UserManager {

    public static ArrayList<User> users = new ArrayList<User>();

    public static void addUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (user.getUid() == users.get(i).getUid()) {
                continue;
            }
            if (i == users.size() - 1) {
                users.add(user);
            }
        }
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUser(int uid) {
        return users.get(uid);
    }
}
