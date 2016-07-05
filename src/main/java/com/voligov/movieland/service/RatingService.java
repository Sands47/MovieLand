package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.User;

import java.util.List;

public interface RatingService {
    void add(Rating rating);

    List<Rating> getRatingForMovie(Movie movie);

    void updateRating(Integer movieId, Double averageRating);

    Rating getRatingForMovie(Movie movie, User user);
}
