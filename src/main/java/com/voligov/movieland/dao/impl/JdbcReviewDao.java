package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.ReviewDao;
import com.voligov.movieland.dao.impl.mapper.ReviewRowMapper;
import com.voligov.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcReviewDao implements ReviewDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getReviewsByMovieIdSQL;

    private final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    @Override
    public List<Review> getByMovieId(int movieId) {
        return jdbcTemplate.query(getReviewsByMovieIdSQL, new Object[]{movieId}, reviewRowMapper);
    }
}
