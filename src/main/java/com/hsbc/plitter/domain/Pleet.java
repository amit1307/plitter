package com.hsbc.plitter.domain;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by garga9 on 21/12/2018.
 */
public class Pleet {

    private Long id;
    private String text;
    private Instant postedTime = Instant.now();
    private User user;

    public Pleet() {
    }

    public Pleet(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Instant getPostedTime() {
        return postedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pleet pleet = (Pleet) o;

        return text.equals(pleet.text);

    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
