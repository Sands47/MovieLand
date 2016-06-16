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

    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public User getUserByEmail(UserCredentials credentials) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(getUserByEmailSQL, new Object[]{credentials.getLogin()}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn("User with email = {} not found in database", credentials.getLogin());
            return null;
        }
        return user;
    }
}
