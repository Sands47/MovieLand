package com.voligov.movieland.util.gson;

import com.google.gson.*;
import com.voligov.movieland.caching.GenreCachingService;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieJsonDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Movie movie = new Movie();
        JsonElement id = jsonObject.get("id");
        if (id != null) {
            movie.setId(id.getAsInt());
        }
        JsonElement name = jsonObject.get("name");
        if (name != null) {
            movie.setName(name.getAsString());
        }
        JsonElement nameOriginal = jsonObject.get("name_original");
        if (nameOriginal != null) {
            movie.setNameOriginal(nameOriginal.getAsString());
        }
        JsonElement releaseYear = jsonObject.get("release_year");
        if (releaseYear != null) {
            movie.setReleaseYear(releaseYear.getAsInt());
        }
        JsonElement description = jsonObject.get("description");
        if (description != null) {
            movie.setDescription(description.getAsString());
        }
        JsonElement price = jsonObject.get("price");
        if (price != null) {
            movie.setPrice(price.getAsDouble());
        }
        JsonElement countries = jsonObject.get("countries");
        if (countries != null) {
            movie.setCountryIds(countries.getAsString());
        }
        JsonElement genres = jsonObject.get("genres");
        if (genres != null) {
            movie.setGenreIds(genres.getAsString());
        }
        return movie;
    }
}
