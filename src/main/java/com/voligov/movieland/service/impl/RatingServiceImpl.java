package com.voligov.movieland.service.impl;

import com.voligov.movieland.caching.RatingCachingService;
import com.voligov.movieland.dao.RatingDao;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private RatingCachingService ratingCachingService;

    @Override
    public boolean add(Rating rating) {
        if (ratingCachingService.addToCache(rating)) {
            ratingDao.add(rating);
            return true;
        }
        return false;
    }

    @Override
    public List<Rating> getRatingsForMovie(Movie movie) {
        return ratingDao.get(movie);
    }

    @Override
    public void updateRating(Movie movie, Double averageRating) {
        ratingDao.updateAverage(movie, averageRating);
    }
}
