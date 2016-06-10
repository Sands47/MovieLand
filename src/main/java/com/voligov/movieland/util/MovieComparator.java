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
            if (movie1.getRating() > movie2.getRating()) {
                result = 1;
            } else if (movie1.getRating() < movie2.getRating()) {
                result = -1;
            }
        } else if ("desc".equals(ratingOrder)) {
            if (movie1.getRating() < movie2.getRating()) {
                result = 1;
            } else if (movie1.getRating() > movie2.getRating()) {
                result = -1;
            }
        }
        if (result == 0 && "asc".equals(priceOrder)) {
            if (movie1.getPrice() > movie2.getPrice()) {
                result = 1;
            } else if (movie1.getPrice() < movie2.getPrice()) {
                result = -1;
            }
        } else if (result == 0 && "desc".equals(priceOrder)) {
            if (movie1.getPrice() < movie2.getPrice()) {
                result = 1;
            } else if (movie1.getPrice() > movie2.getPrice()) {
                result = -1;
            }
        }
        return result;
    }
}
