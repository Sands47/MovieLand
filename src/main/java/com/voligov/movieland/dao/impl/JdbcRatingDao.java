package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.RatingDao;
import com.voligov.movieland.dao.impl.mapper.RatingRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
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
    private String getRatingSQL;

    @Autowired
    private String updateRatingSQL;

    @Autowired
    private String updateAvgRatingSQL;

    private RatingRowMapper ratingRowMapper = new RatingRowMapper();

    @Override
    public void add(Rating rating) {
        jdbcTemplate.update(addRatingSQL, rating.getMovie().getId(), rating.getUser().getId(), rating.getRating());
        log.info("Rating {} added to database", rating);
    }

    @Override
    public void update(Rating rating) {
        jdbcTemplate.update(updateRatingSQL, rating.getRating(), rating.getMovie().getId(), rating.getUser().getId());
        log.info("Rating {} updated in database", rating);
    }

    @Override
    public List<Rating> get(Movie movie) {
        return jdbcTemplate.query(getRatingSQL, ratingRowMapper, movie.getId());
    }

    @Override
    public void updateAverage(Integer movieId, Double averageRating) {
        jdbcTemplate.update(updateAvgRatingSQL, averageRating, movieId);
        log.info("Rating for movie id = {} updated to {} to database", movieId, averageRating);
    }
}
