package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public List<Review> getByMovieId(int movieId) {
        return reviewDao.getByMovieId(movieId);
    }

    @Override
    public boolean add(Review review, String token) {
        return securityService.validateToken(token, review.getUser()) && reviewDao.add(review);
    }

    @Override
    public boolean delete(Review review, String token) {
        return securityService.validateToken(token, review.getUser()) && reviewDao.delete(review);
    }
}
