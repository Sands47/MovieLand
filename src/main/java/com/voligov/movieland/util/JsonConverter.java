package com.voligov.movieland.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voligov.movieland.entity.Movie;
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
        log.info("Start converting a list of movies to JSON array");
        String json = gsonGetAllMovies.toJson(movies);
        log.info("Finished converting a list of movies to JSON: {}", json);
        return json;
    }

    public String toJson(Movie movie) {
        log.info("Start converting a movie with Id = {} to JSON", movie.getId());
        String json = gsonGetMovieById.toJson(movie);
        log.info("Finished converting movie to JSON: {}", json);
        return json;
    }

    public Map<String, String> parseSearchParams(String json) {
        Map<String, String> parameters = new HashMap<>();
        parameters = gson.fromJson(json, parameters.getClass());
        return parameters;
    }
}
