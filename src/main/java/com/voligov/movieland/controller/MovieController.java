package com.voligov.movieland.controller;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.MovieService;
import com.voligov.movieland.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getAllMovies() {
        log.info("Received request to get list of all movies");
        long startTime = System.currentTimeMillis();
        List<Movie> movies = movieService.getAll();
        String json = jsonConverter.toJson(movies);
        log.info("List of movies received. It took {} ms", System.currentTimeMillis() - startTime);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "/{movieId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getMovieById(@PathVariable int movieId) {
        log.info("Received request to get movie with id = {}", movieId);
        long startTime = System.currentTimeMillis();
        Movie movie = movieService.getById(movieId);
        if (movie == null) {
            return new ResponseEntity<>("Movie not found in database", HttpStatus.BAD_REQUEST);
        } else {
            String json = jsonConverter.toJson(movie);
            log.info("Movie received. It took {} ms", System.currentTimeMillis() - startTime);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> searchMovies(@RequestBody String json) {
        List<Movie> movies = movieService.search(jsonConverter.parseSearchParams(json));
        if (movies.isEmpty()) {
            return new ResponseEntity<>("Movies not found in database", HttpStatus.BAD_REQUEST);
        } else {
            String jsonResponse = jsonConverter.toJson(movies);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
    }
}
