package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.enums.SortingOrder;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {
    private SortingOrder ratingOrder;
    private SortingOrder priceOrder;

    public MovieComparator(String ratingOrder, String priceOrder) {
        if (ratingOrder != null) {
            this.ratingOrder = SortingOrder.valueOf(ratingOrder.toUpperCase());
        }
        if (priceOrder != null) {
            this.priceOrder = SortingOrder.valueOf(priceOrder.toUpperCase());
        }
    }

    @Override
    public int compare(Movie movie1, Movie movie2) {
        int result = 0;
        if (SortingOrder.ASC == ratingOrder) {
            result = movie1.getRating().compareTo(movie2.getRating());
        } else if (SortingOrder.DESC == ratingOrder) {
            result = -movie1.getRating().compareTo(movie2.getRating());
        }
        if (result == 0 && SortingOrder.ASC == priceOrder) {
            result = movie1.getPrice().compareTo(movie2.getPrice());
        } else if (result == 0 && SortingOrder.DESC == priceOrder) {
            result = -movie1.getPrice().compareTo(movie2.getPrice());
        }
        return result;
    }
}
