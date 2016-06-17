package com.voligov.movieland.entity;

public class Rating {

    private User user;
    private Movie movie;
    private Integer rating;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "user=" + user +
                ", movie=" + movie +
                ", rating=" + rating +
                '}';
    }
}
