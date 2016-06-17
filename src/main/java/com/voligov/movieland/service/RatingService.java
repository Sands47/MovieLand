package com.voligov.movieland.service;

import com.voligov.movieland.entity.Rating;

public interface RatingService {
    boolean add(Rating rating, String token);
}
