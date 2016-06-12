package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.dao.impl.mapper.ReviewRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

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
    private String getReviewsByMovieIdSQL;

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    @Override
    public List<Movie> getAll() {
        List<Movie> movieList = jdbcTemplate.query(getAllMoviesSQL, movieRowMapper);
        return movieList;
    }

    @Override
    public Movie getById(int id) {
        Movie movie;
        try {
            movie = jdbcTemplate.queryForObject(getMovieByIdSQL, new Object[]{id}, movieRowMapper);
        } catch (EmptyResultDataAccessException e) {
            log.info("Movie with id = {} not found in database", id);
            return null;
        }
        List<Review> reviewList = jdbcTemplate.query(getReviewsByMovieIdSQL, new Object[]{id}, reviewRowMapper);
        movie.setReviews(reviewList);
        return movie;
    }

    @Override
    public List<Movie> search(Map<String, String> searchParams) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if (!searchParams.isEmpty()) {
            builder.append("WHERE ");
            builder.append(addQueryCondition("genres", searchParams.get("genre")));
            builder.append(addQueryCondition("countries", searchParams.get("country")));
            builder.append(addQueryCondition("name", searchParams.get("title")));
            builder.append(addQueryCondition("name_original", searchParams.get("title_original")));
            builder.append(addQueryCondition("release_year", searchParams.get("release_year")));
            builder.delete(builder.length() - 5, builder.length());
        }
        return jdbcTemplate.query(builder.toString(), movieRowMapper);
    }

    private String addQueryCondition(String field, String value) {
        if (value == null) {
            return "";
        }
        return field + " LIKE '%" + value + "%' AND ";
    }
}
