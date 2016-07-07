package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.User;

import java.util.List;

public interface RatingDao {
    void add(Rating rating);

    void update(Rating rating);

    List<Rating> get(Movie movie);

    Rating get(Movie movie, User user);

    void updateAverage(Integer movieId, Double averageRating);
}
