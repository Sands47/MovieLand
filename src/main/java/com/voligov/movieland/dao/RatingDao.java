package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;

import java.util.List;

public interface RatingDao {
    void add(Rating rating);

    //void updateAverage(Movie movie);

    List<Rating> get(Movie movie);

    void updateAverage(Movie movie, Double averageRating);
}
