package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.UserDao;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserByReviewId(Integer reviewId) {
        return userDao.getUserByReviewId(reviewId);
    }
}
