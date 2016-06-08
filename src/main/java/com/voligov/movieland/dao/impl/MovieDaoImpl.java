package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieDaoImpl implements MovieDao{
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getAllMovies;

    public List<Movie> getAll() {
        log.info("Started query to get all movies from database");
        long startTime = System.currentTimeMillis();
        List<Movie> movieList =  jdbcTemplate.query(getAllMovies, new MovieRowMapper());
        log.info("Finished query to get all movies from database. It took {} ms", System.currentTimeMillis() - startTime);
        return movieList;
    }
}
