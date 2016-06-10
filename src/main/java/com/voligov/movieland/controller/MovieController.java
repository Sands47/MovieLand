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
import java.util.Map;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getAllMovies() {
        List<Movie> movies = movieService.getAll();
        if (movies.isEmpty()) {
            return new ResponseEntity<>("Movies not found in database", HttpStatus.BAD_REQUEST);
        }
        String json = jsonConverter.toJson(movies);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "/{movieId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getMovieById(@PathVariable int movieId) {
        Movie movie = movieService.getById(movieId);
        if (movie == null) {
            return new ResponseEntity<>("Movie not found in database", HttpStatus.BAD_REQUEST);
        } else {
            String json = jsonConverter.toJson(movie);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> searchMovies(@RequestBody String json) {
        Map<String, String> searchParams = jsonConverter.parseSearchParams(json);
        if (searchParams == null) {
            return new ResponseEntity<>("Incorrect JSON", HttpStatus.BAD_REQUEST);
        }
        List<Movie> movies = movieService.search(searchParams);
        if (movies.isEmpty()) {
            return new ResponseEntity<>("Movies not found in database", HttpStatus.BAD_REQUEST);
        }
        String jsonResponse = jsonConverter.toJson(movies);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
