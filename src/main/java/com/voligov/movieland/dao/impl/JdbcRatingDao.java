package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.RatingDao;
import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.dao.impl.mapper.RatingRowMapper;
import com.voligov.movieland.dao.impl.mapper.ReviewRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcRatingDao implements RatingDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String addRatingSQL;

    @Autowired
    private String updateRatingSQL;

    @Autowired
    private String checkRatingSQL;

    @Autowired
    private String avgRatingSQL;

    @Autowired
    private String updateAvgRatingSQL;

    @Override
    public void add(Rating rating) {
        Integer ratingCount = jdbcTemplate.queryForObject(checkRatingSQL, Integer.class, rating.getMovie().getId(), rating.getUser().getId());
        if (ratingCount == 0) {
            jdbcTemplate.update(addRatingSQL, rating.getMovie().getId(), rating.getUser().getId(), rating.getRating());
            log.info("Rating {} added to database", rating);
        } else {
            jdbcTemplate.update(updateRatingSQL, rating.getRating(), rating.getMovie().getId(), rating.getUser().getId());
            log.info("Rating {} updated in database", rating);
        }
    }

    @Override
    public void updateAverage(Movie movie) {
        Double averageCount = jdbcTemplate.queryForObject(avgRatingSQL, Double.class, movie.getId());
        jdbcTemplate.update(updateAvgRatingSQL, averageCount, movie.getId());
        log.info("Rating for movie {} updated to {} to database", movie, averageCount);
    }
}
