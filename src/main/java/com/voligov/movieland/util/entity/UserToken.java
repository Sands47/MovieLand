package com.voligov.movieland.util.entity;

import com.voligov.movieland.entity.User;

import java.util.Date;

public class UserToken {
    private User user;
    private String token;
    private Date expirationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserToken)) {
            return false;
        }
        UserToken token = (UserToken) obj;
        return user.equals(token.getUser());
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "user=" + user +
                ", token=" + token +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
