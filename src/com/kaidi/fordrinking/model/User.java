package com.kaidi.fordrinking.model;

/**
 * Author: kaidi
 * Date: 01/29, 2015
 */
public class User {
    private int uid;
    private String username;
    private String email;
    private String avatar;
    private String password;

    public User(int uid, String username, String email, String password, String avatar) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
