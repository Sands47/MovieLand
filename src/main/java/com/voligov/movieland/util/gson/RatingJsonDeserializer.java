package com.voligov.movieland.util.gson;

import com.google.gson.*;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.Constant;

import java.lang.reflect.Type;

public class RatingJsonDeserializer implements JsonDeserializer<Rating> {
    @Override
    public Rating deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Rating rating = new Rating();
        JsonElement ratingElement = jsonObject.get(Constant.RATING);
        if (ratingElement != null) {
            rating.setRating(ratingElement.getAsInt());
        }
        JsonElement movieId = jsonObject.get(Constant.MOVIE_ID);
        if (movieId != null) {
            Movie movie = new Movie();
            movie.setId(movieId.getAsInt());
            rating.setMovie(movie);
        }
        JsonElement userId = jsonObject.get(Constant.USER_ID);
        if (userId != null) {
            User user = new User();
            user.setId(userId.getAsInt());
            rating.setUser(user);
        }
        return rating;
    }
}
