package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public List<Review> getByMovieId(int movieId) {
        return reviewDao.getByMovieId(movieId);
    }

    @Override
    public boolean add(Review review, User user) {
        if (user.getId().equals(review.getUser().getId())
                || user.getRole() == UserRole.ADMIN) {
            return reviewDao.add(review);
        } else {
            throw new SecurityException("You can only add reviews for your own user");
        }
    }

    @Override
    public boolean delete(Review review, User user) {
        if (user.getId().equals(review.getUser().getId())
                || user.getRole() == UserRole.ADMIN) {
            return reviewDao.delete(review);
        } else {
            throw new SecurityException("You can only delete your own review");
        }
    }
}
