package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.service.UserService;
import com.voligov.movieland.util.enums.UserRole;
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
    public boolean add(Review review) {
        return reviewDao.add(review);
    }

    @Override
    public void delete(Review review) {
        reviewDao.delete(review);
    }

    @Override
    public User getAuthor(Review review) {
        return userService.getUserByReviewId(review.getId());
    }

    @Override
    public void deleteReviewsForMovies(String movies) {
        reviewDao.deleteReviewsForMovies(movies);
    }

}
