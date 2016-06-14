package com.voligov.movieland.entity;

import java.util.Date;
import java.util.UUID;

public class UserToken {
    private User user;
    private UUID token;
    private Date expirationDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
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
