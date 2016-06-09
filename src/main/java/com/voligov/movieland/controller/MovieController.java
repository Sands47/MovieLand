package com.voligov.movieland.controller;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.MovieService;
import com.voligov.movieland.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(value = "/movies", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getAllMovies() {
        log.info("Received request to get list of all movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.getAll();
        String json = jsonConverter.toJson(movies);
        log.info("List of movies received. It took {} ms", System.currentTimeMillis() - startTime);
        return json;
    }

    @RequestMapping(value = "/movie/{movieId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getMovieById(@PathVariable int movieId) {
        log.info("Received request to get movie with id = {}", movieId);
        long startTime = System.currentTimeMillis();
        Movie movie = movieService.getById(movieId);
        String json = jsonConverter.toJson(movie);
        log.info("Movie received. It took {} ms", System.currentTimeMillis() - startTime);
        return json;
    }
}
