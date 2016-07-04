package com.voligov.movieland.dao;

import com.voligov.movieland.entity.User;

public interface UserDao {
    User getUserByEmail(String email);

    User getUserByReviewId(Integer reviewId);
}
