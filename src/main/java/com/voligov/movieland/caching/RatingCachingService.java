package com.voligov.movieland.caching;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.service.RatingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RatingCachingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RatingService ratingService;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    private Map<Movie, List<Rating>> ratings = new HashMap<>();

    public boolean addToCache(Rating rating) {
        List<Rating> ratingList;
        try {
            readLock.lock();
            ratingList = ratings.get(rating.getMovie());
            if (ratingList == null) {
                try {
                    readLock.unlock();
                    writeLock.lock();
                    ratingList = ratings.get(rating.getMovie());
                    if (ratingList == null) {
                        ratingList = ratingService.getRatingsForMovie(rating.getMovie());
                        ratings.put(rating.getMovie(), ratingList);
                    }
                } finally {
                    writeLock.unlock();
                    readLock.lock();
                }
            }
            int index = ratingList.indexOf(rating);
            if (index != -1) {
                log.info("Rating for movie Id = {} by user Id = {} already exists",
                        rating.getMovie().getId(), rating.getUser().getId());
                return false;
            }
        } finally {
            readLock.unlock();
        }
        try {
            writeLock.lock();
            ratingList.add(rating);
            log.info("Rating for movie Id = {} by user Id = {} added to cache",
                    rating.getMovie().getId(), rating.getUser().getId());
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void flushToDb() {
        try {
            writeLock.lock();
            for (Map.Entry<Movie, List<Rating>> movieListEntry : ratings.entrySet()) {
                Double averageRating = calculateAverage(movieListEntry.getValue());
                ratingService.updateRating(movieListEntry.getKey(), averageRating);
                log.info("Rating for movie Id = {} updated to {} in database",
                        movieListEntry.getKey().getId(), averageRating);
            }
            ratings.clear();
        } finally {
            writeLock.unlock();
        }
    }

    private Double calculateAverage(List<Rating> ratingList) {
        Double sum = 0.0;
        for (Rating rating : ratingList) {
            sum += rating.getRating();
        }
        return sum / ratingList.size();
    }
}
