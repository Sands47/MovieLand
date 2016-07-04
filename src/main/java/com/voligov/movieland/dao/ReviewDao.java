package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;

import java.util.List;

public interface ReviewDao {
    List<Review> getByMovieId(int movieId);

    boolean add(Review review);

    void delete(Review review);

    void deleteReviewsForMovies(List<Integer> movies);
}
