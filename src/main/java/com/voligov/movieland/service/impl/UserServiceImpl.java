package com.voligov.movieland.service.impl;

import com.voligov.movieland.caching.UserTokenCachingService;
import com.voligov.movieland.dao.UserDao;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.UserService;
import com.voligov.movieland.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private UserTokenCachingService userTokenCachingService;

    @Override
    public User getUser(UserCredentials credentials) {
        User user = userDao.getUserByEmail(credentials);
        return user;
    }

    @Override
    public boolean validateUser(UserCredentials credentials, User user) {
        if (user.getEmail().equals(credentials.getLogin()) &&
                user.getPassword().equals(credentials.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateToken(String token, User user) {
        UserToken userToken = userTokenCachingService.getByTokenString(token);
        return (userToken != null) && (user.getId().equals(userToken.getUser().getId()));
    }

    @Override
    public UserToken generateToken(User user) {
        UserToken token = userUtils.generateToken(user);
        token = userTokenCachingService.addToCache(token);
        return token;
    }
}
