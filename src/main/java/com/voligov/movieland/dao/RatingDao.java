package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;

import java.util.List;

public interface RatingDao {
    void add(Rating rating);

    void update(Rating rating);

    List<Rating> get(Movie movie);

    void updateAverage(Integer movieId, Double averageRating);
}
