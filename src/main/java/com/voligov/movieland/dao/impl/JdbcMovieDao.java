package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.gson.MovieSearchParams;
import com.voligov.movieland.util.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getAllMoviesSQL;

    @Autowired
    private String getMovieByIdSQL;

    private final QueryBuilder queryBuilder = new QueryBuilder();

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(getAllMoviesSQL, movieRowMapper);
    }

    @Override
    public Movie getById(int id) {
        Movie movie;
        try {
            movie = jdbcTemplate.queryForObject(getMovieByIdSQL, new Object[]{id}, movieRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Movie with id = {} not found in database", id);
            return null;
        }
        return movie;
    }

    @Override
    public List<Movie> search(MovieSearchParams searchParams) {
        return jdbcTemplate.query(queryBuilder.buildSearchQuery(searchParams, getAllMoviesSQL), movieRowMapper);
    }
}
