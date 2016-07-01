package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.enums.SortingOrder;
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

    @Autowired
    private String addMovieSQL;

    @Autowired
    private String getMovieIdSQL;

    @Autowired
    private String updateMovieSQL;

    @Autowired
    private String deleteMoviesSQL;

    private final QueryBuilder queryBuilder = new QueryBuilder();

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();

    @Override
    public List<Movie> getAll(int page, SortingOrder ratingOrder, SortingOrder priceOrder) {
        String query = queryBuilder.buildPagedQuery(page, ratingOrder, priceOrder, getAllMoviesSQL);
        return jdbcTemplate.query(query, movieRowMapper);
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
        String query = queryBuilder.buildSearchQuery(searchParams, getAllMoviesSQL);
        return jdbcTemplate.query(query, movieRowMapper);
    }

    @Override
    public void add(Movie movie) {
        jdbcTemplate.update(addMovieSQL, movie.getName(), movie.getNameOriginal(), movie.getReleaseYear(),
                movie.getDescription(), movie.getPrice());
        Integer movieId = jdbcTemplate.queryForObject(getMovieIdSQL, Integer.class, movie.getName(), movie.getNameOriginal());
        log.info("Movie {} added to database. Id {} generated", movie, movieId);
        movie.setId(movieId);
    }

    @Override
    public void edit(Movie movie) {
        jdbcTemplate.update(updateMovieSQL, movie.getName(), movie.getNameOriginal(), movie.getReleaseYear(),
                movie.getDescription(), movie.getPrice(), movie.getId());
        log.info("Movie {} updated in database", movie.getId());
    }

    @Override
    public void deleteMovies(String movies) {
        jdbcTemplate.update(deleteMoviesSQL, movies);
    }
}
