package com.voligov.movieland.util.entity;


import com.voligov.movieland.entity.User;

public class GetMovieByIdRequestParams {
    private int movieId;
    private String currency;
    private User user;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
