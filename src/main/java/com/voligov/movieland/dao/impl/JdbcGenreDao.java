package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.GenreDao;
import com.voligov.movieland.dao.impl.mapper.GenreRowMapper;
import com.voligov.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getGenresSQL;

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(getGenresSQL, genreRowMapper);
    }
}