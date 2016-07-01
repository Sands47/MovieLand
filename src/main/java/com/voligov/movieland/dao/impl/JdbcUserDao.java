package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.UserDao;
import com.voligov.movieland.dao.impl.mapper.UserRowMapper;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.entity.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcUserDao implements UserDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getUserByEmailSQL;

    @Autowired
    private String getReviewAuthorSQL;

    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public User getUserByEmail(String email) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(getUserByEmailSQL, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User with email = {} not found in database", email);
            return null;
        }
        return user;
    }

    @Override
    public User getUserByReviewId(Integer reviewId) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(getReviewAuthorSQL, userRowMapper, reviewId);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Author of review = {} not found in database", reviewId);
            return null;
        }
        return user;
    }
}
