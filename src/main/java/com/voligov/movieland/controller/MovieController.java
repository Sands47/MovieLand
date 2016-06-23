package com.voligov.movieland.controller;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.UserToken;
import com.voligov.movieland.service.SecurityService;
import com.voligov.movieland.util.enums.UserRole;
import com.voligov.movieland.util.gson.MovieSearchParams;
import com.voligov.movieland.service.MovieService;
import com.voligov.movieland.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getAllMovies(@RequestParam(value = "rating", required = false) String ratingOrder,
                                               @RequestParam(value = "price", required = false) String priceOrder) {
        List<Movie> movies = movieService.getAll(ratingOrder, priceOrder);
        String json = jsonConverter.toJson(movies);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> addMovie(@RequestHeader("token") String token, @RequestBody String json) {
        try {
            UserToken userToken = securityService.validateToken(token);
            if (userToken.getUser().getRole() != UserRole.ADMIN) {
                return new ResponseEntity<>(jsonConverter.wrapResponse("Only admins can add movies"), HttpStatus.UNAUTHORIZED);
            }
            Movie movie = jsonConverter.parseMovie(json);
            movieService.add(movie);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SecurityException e) {
            return new ResponseEntity<>(jsonConverter.wrapResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/{movieId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getMovieById(@PathVariable int movieId) {
        Movie movie = movieService.getById(movieId);
        if (movie == null) {
            return new ResponseEntity<>(jsonConverter.wrapResponse("Movie not found in database"), HttpStatus.BAD_REQUEST);
        } else {
            String json = jsonConverter.toJson(movie);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> searchMovies(@RequestBody String json) {
        MovieSearchParams searchParams = jsonConverter.parseSearchParams(json);
        List<Movie> movies = movieService.search(searchParams);
        String jsonResponse = jsonConverter.toJson(movies);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
