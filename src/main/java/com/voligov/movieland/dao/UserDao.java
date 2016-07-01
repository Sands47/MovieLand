package com.voligov.movieland.dao;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;

public interface UserDao {
    User getUserByEmail(String email);

    User getUserByReviewId(Integer reviewId);
}
