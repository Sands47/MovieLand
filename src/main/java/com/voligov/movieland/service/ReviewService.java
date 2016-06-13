package com.voligov.movieland.service;

import com.voligov.movieland.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getByMovieId(int movieId);
}
