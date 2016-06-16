package com.voligov.movieland.service;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.entity.UserToken;

public interface SecurityService {
    boolean validateUser(UserCredentials credentials, User user);

    boolean validateToken(String token, User user);

    UserToken registerUser(User user);
}