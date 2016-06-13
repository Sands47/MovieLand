package com.voligov.movieland.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.MovieSearchParams;
import com.voligov.movieland.entity.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JsonConverter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final List<String> GET_ALL_MOVIES_RESPONSE_FIELDS = Arrays.asList("name", "nameOriginal", "releaseYear", "rating", "genres");
    private static final List<String> GET_MOVIE_BY_ID_RESPONSE_FIELDS = Arrays.asList("name", "nameOriginal", "releaseYear", "countries", "genres",
            "description", "reviews", "rating", "user", "firstName", "lastName", "text");

    private Gson gson = new Gson();
    private Gson gsonGetAllMovies = new GsonBuilder().setExclusionStrategies(new FieldExclusionStrategy(GET_ALL_MOVIES_RESPONSE_FIELDS)).create();
    private Gson gsonGetMovieById = new GsonBuilder().setExclusionStrategies(new FieldExclusionStrategy(GET_MOVIE_BY_ID_RESPONSE_FIELDS)).create();

    public String toJson(List<Movie> movies) {
        return gsonGetAllMovies.toJson(movies);
    }

    public String toJson(Movie movie) {
        return gsonGetMovieById.toJson(movie);
    }

    public MovieSearchParams parseSearchParams(String json) {
        try {
            return gson.fromJson(json, MovieSearchParams.class);
        } catch (Exception e) {
            log.warn("Error parsing JSON: {} ", e);
            throw e;
        }
    }

    public UserCredentials parseUserCredentials(String json) {
        try {
            return gson.fromJson(json, UserCredentials.class);
        } catch (Exception e) {
            log.warn("Error parsing JSON: {} ", e);
            throw e;
        }
    }
}
