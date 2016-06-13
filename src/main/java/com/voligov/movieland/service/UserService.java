package com.voligov.movieland.service;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.entity.UserToken;

public interface UserService {
    User getUser(UserCredentials credentials);

    boolean validateUser(UserCredentials credentials, User user);

    UserToken generateToken(User user);
}
