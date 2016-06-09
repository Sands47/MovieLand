package com.voligov.movieland.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voligov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JsonConverter {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final List<String> GET_ALL_MOVIES_RESPONSE_FIELDS = Arrays.asList("name", "nameOriginal", "releaseYear", "rating", "genres");

    private Gson gsonGetAllMovies = new GsonBuilder().setExclusionStrategies(new FieldExclusionStrategy(GET_ALL_MOVIES_RESPONSE_FIELDS)).create();

    public String toJson(List<Movie> movies) {
        log.info("Start converting a list of movies to JSON array");
        String json = gsonGetAllMovies.toJson(movies);
        log.info("Finished converting a list of movies to JSON: {}", json);
        return json;
    }
}
