package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {
    private String ratingOrder;
    private String priceOrder;

    public MovieComparator(String ratingOrder, String priceOrder) {
        this.ratingOrder = ratingOrder;
        this.priceOrder = priceOrder;
    }

    @Override
    public int compare(Movie movie1, Movie movie2) {
        int result = 0;
        if ("asc".equals(ratingOrder)) {
            result = movie1.getRating().compareTo(movie2.getRating());
        } else if ("desc".equals(ratingOrder)) {
            result = -movie1.getRating().compareTo(movie2.getRating());
        }
        if (result == 0 && "asc".equals(priceOrder)) {
            result = movie1.getPrice().compareTo(movie2.getPrice());
        } else if (result == 0 && "desc".equals(priceOrder)) {
            result = -movie1.getPrice().compareTo(movie2.getPrice());
        }
        return result;
    }
}
