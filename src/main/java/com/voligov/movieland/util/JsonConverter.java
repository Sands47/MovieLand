package com.voligov.movieland.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.util.gson.MovieSearchParams;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.UserCredentials;
import com.voligov.movieland.util.gson.FieldExclusionStrategy;
import com.voligov.movieland.util.gson.RatingJsonDeserializer;
import com.voligov.movieland.util.gson.ReviewJsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class JsonConverter {
    private static final List<String> GET_ALL_MOVIES_RESPONSE_FIELDS = Arrays.asList("name", "nameOriginal", "releaseYear", "rating", "genres", "name");
    private static final List<String> GET_MOVIE_BY_ID_RESPONSE_FIELDS = Arrays.asList("name", "nameOriginal", "releaseYear", "countries", "genres", "name",
            "description", "reviews", "rating", "user", "firstName", "lastName", "text");

    private Gson gson = new Gson();
    private Gson gsonGetAllMovies = new GsonBuilder().setExclusionStrategies(new FieldExclusionStrategy(GET_ALL_MOVIES_RESPONSE_FIELDS)).create();
    private Gson gsonGetMovieById = new GsonBuilder().setExclusionStrategies(new FieldExclusionStrategy(GET_MOVIE_BY_ID_RESPONSE_FIELDS)).create();
    private Gson gsonParseReview = new GsonBuilder().registerTypeAdapter(Review.class, new ReviewJsonDeserializer()).create();
    private Gson gsonParseRating = new GsonBuilder().registerTypeAdapter(Rating.class, new RatingJsonDeserializer()).create();

    public String toJson(List<Movie> movies) {
        return gsonGetAllMovies.toJson(movies);
    }

    public String toJson(Movie movie) {
        return gsonGetMovieById.toJson(movie);
    }

    public MovieSearchParams parseSearchParams(String json) {
        return gson.fromJson(json, MovieSearchParams.class);
    }

    public UserCredentials parseUserCredentials(String json) {
        return gson.fromJson(json, UserCredentials.class);
    }

    public Review parseReview(String json) {
        return gsonParseReview.fromJson(json, Review.class);
    }

    public Rating parseRating(String json) {
        return gsonParseRating.fromJson(json, Rating.class);
    }

    public String wrapResponse(String response) {
        return gson.toJson(response, String.class);
    }
}
