package com.voligov.movieland.entity;

import java.util.Date;
import java.util.UUID;

public class UserToken {
    private User user;
    private UUID token;
    private Date creationDate;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "user=" + user +
                ", token=" + token +
                ", creationDate=" + creationDate +
                '}';
    }
}
