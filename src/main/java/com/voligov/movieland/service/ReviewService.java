package com.voligov.movieland.service;

import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;

import java.util.List;

public interface ReviewService {
    List<Review> getByMovieId(int movieId);

    boolean add(Review review);

    void delete(Review review);

    User getAuthor(Review review);

    void deleteReviewsForMovies(String movies);
}
