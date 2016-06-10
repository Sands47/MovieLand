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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Autowired
    private String searchMoviesSQL;

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    private Random random = new Random();

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
        if (reviewList.size() > 2) {
            List<Review> randomReviews = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                int index = random.nextInt(reviewList.size());
                randomReviews.add(reviewList.get(index));
                reviewList.remove(index);
            }
            reviewList = randomReviews;
        }
        movie.setReviews(reviewList);
        return movie;
    }

    @Override
    public List<Movie> search(Map<String, String> searchParams) {
        String genre = addPercentSigns(searchParams.get("genre"));
        String country = addPercentSigns(searchParams.get("country"));
        String title = addPercentSigns(searchParams.get("title"));
        String releaseYear = addPercentSigns(searchParams.get("release_year"));
        return jdbcTemplate.query(searchMoviesSQL, new Object[]{title, title, releaseYear, genre, country}, movieRowMapper);
    }

    private String addPercentSigns(String value) {
        if (value == null) {
            return "%";
        }
        return "%" + value + "%";
    }
}
