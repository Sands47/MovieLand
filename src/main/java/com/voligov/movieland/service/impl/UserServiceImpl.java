package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.UserDao;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public User getUser(UserCredentials credentials) {
        return userDao.getUserByEmail(credentials);
    }

    @Override
    public String authoriseUser(UserCredentials credentials) {
        if (credentials.isInvalid()) {
            throw new SecurityException("User credentials are invalid");
        }
        User user = getUser(credentials);
        if (user == null || !securityService.validateUser(credentials, user)) {
            throw new SecurityException("Login or password are invalid");
        }
        return securityService.registerUser(user).getToken();
    }
}
