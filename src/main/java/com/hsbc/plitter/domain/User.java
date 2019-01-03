package com.hsbc.plitter.domain;

/**
 * Created by garga9 on 27/12/2018.
 */
public class User {


    private long userId;
    private String userName;

    public User() {
    }

    public User(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
