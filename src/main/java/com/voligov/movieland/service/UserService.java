package com.voligov.movieland.service;

import com.voligov.movieland.entity.User;

public interface UserService {
    User getUser(String email);

    User getUserByReviewId(Integer reviewId);
}
