package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.dao.impl.mapper.ReviewRowMapper;
import com.voligov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getReviewsByMovieIdSQL;

    @Autowired
    private String addReviewSQL;

    @Autowired
    private String deleteReviewSQL;

    @Autowired
    private String getReviewSQL;

    private final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    @Override
    public List<Review> getByMovieId(int movieId) {
        return jdbcTemplate.query(getReviewsByMovieIdSQL, new Object[]{movieId}, reviewRowMapper);
    }

    @Override
    public boolean add(Review review) {
        Integer reviewCount = jdbcTemplate.queryForObject(getReviewSQL, Integer.class, review.getMovie().getId(), review.getUser().getId());
        if (reviewCount == 0) {
            jdbcTemplate.update(addReviewSQL, review.getMovie().getId(), review.getUser().getId(), review.getText());
            log.info("Review {} added to database", review);
            return true;
        } else {
            log.info("Review by user {} for movie {} already exists in the database", review.getUser().getId(), review.getMovie().getId());
            return false;
        }
    }

    @Override
    public boolean delete(Review review) {
        Integer reviewCount = jdbcTemplate.queryForObject(getReviewSQL, Integer.class, review.getMovie().getId(), review.getUser().getId());
        if (reviewCount != 0) {
            jdbcTemplate.update(deleteReviewSQL, review.getMovie().getId(), review.getUser().getId());
            log.info("Review {} deleted from database", review);
            return true;
        } else {
            log.info("Review by user {} for movie {} doesn't exists in the database", review.getUser().getId(), review.getMovie().getId());
            return false;
        }
    }
}