package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getByMovieId(int movieId);

    boolean add(Review review);

    boolean delete(Review review);
}
