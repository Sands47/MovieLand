package com.voligov.movieland.service;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.entity.UserCredentials;
import com.voligov.movieland.util.entity.UserToken;
import com.voligov.movieland.util.enums.UserRole;

public interface SecurityService {
    boolean validateUser(UserCredentials credentials, User user);

    UserToken validateToken(String token, UserRole roleRequired);

    UserToken registerUser(User user);
}
