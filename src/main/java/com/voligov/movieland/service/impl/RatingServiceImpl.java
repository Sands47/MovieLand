package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.RatingDao;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Override
    public boolean add(Rating rating) {
        if (rating.getRating() > 10 || rating.getRating() < 0) {
            return false;
        } else {
            ratingDao.add(rating);
            ratingDao.updateAverage(rating.getMovie());
            return true;
        }
    }
}
