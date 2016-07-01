package com.voligov.movieland.util.gson;

import com.google.gson.*;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.Constant;

import java.lang.reflect.Type;

public class ReviewJsonDeserializer implements JsonDeserializer<Review> {
    @Override
    public Review deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Review review = new Review();
        JsonElement id = jsonObject.get(Constant.ID);
        if (id != null) {
            review.setId(id.getAsInt());
        }
        JsonElement text = jsonObject.get(Constant.TEXT);
        if (text != null) {
            review.setText(text.getAsString());
        }
        JsonElement movieId = jsonObject.get(Constant.MOVIE_ID);
        if (movieId != null) {
            Movie movie = new Movie();
            movie.setId(movieId.getAsInt());
            review.setMovie(movie);
        }
        JsonElement userId = jsonObject.get(Constant.USER_ID);
        if (userId != null) {
            User user = new User();
            user.setId(userId.getAsInt());
            review.setUser(user);
        }
        return review;
    }
}
