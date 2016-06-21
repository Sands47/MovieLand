package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;

import java.util.List;

public interface RatingService {
    boolean add(Rating rating);

    List<Rating> getRatingsForMovie(Movie movie);

    void updateRating(Movie movie, Double averageRating);
}
