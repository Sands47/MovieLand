package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;

public interface RatingDao {
    void add(Rating rating);

    void updateAverage(Movie movie);
}
