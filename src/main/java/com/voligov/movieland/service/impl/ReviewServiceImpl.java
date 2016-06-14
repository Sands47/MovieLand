package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private UserService userService;

    @Override
    public List<Review> getByMovieId(int movieId) {
        return reviewDao.getByMovieId(movieId);
    }

    @Override
    public boolean add(Review review, String token) {
        if (userService.validateToken(token)) {
            return reviewDao.add(review);
        }
        return false;
    }

    @Override
    public boolean delete(Review review, String token) {
        if (userService.validateToken(token)) {
            return reviewDao.delete(review);
        }
        return false;
    }
}
