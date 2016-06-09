package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.dao.impl.mapper.ReviewRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final ReviewRowMapper reviewRowMapper = new ReviewRowMapper();

    private Random random = new Random();

    @Override
    public List<Movie> getAll() {
        log.info("Started query to get all movies from database");
        long startTime = System.currentTimeMillis();
        List<Movie> movieList = jdbcTemplate.query(getAllMoviesSQL, movieRowMapper);
        log.info("Finished query to get all movies from database. It took {} ms", System.currentTimeMillis() - startTime);
        return movieList;
    }

    @Override
    public Movie getById(int id) {
        log.info("Started query to get movie with id = {} from database", id);
        long startTime = System.currentTimeMillis();
        Movie movie = jdbcTemplate.queryForObject(getMovieByIdSQL, new Object[]{id}, movieRowMapper);
        List<Review> reviewList = jdbcTemplate.query(getReviewsByMovieIdSQL, reviewRowMapper);
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
        log.info("Finished query to get movie from database. It took {} ms", System.currentTimeMillis() - startTime);
        return null;
    }
}
