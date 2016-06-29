package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;

import java.util.List;

public interface RatingService {
    void add(Rating rating);

    List<Rating> getRatingsForMovie(Movie movie);

    void updateRating(Integer movieId, Double averageRating);
}
