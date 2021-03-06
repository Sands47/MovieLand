package com.voligov.movieland.util.gson;

import com.google.gson.*;
import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.voligov.movieland.util.Constant.*;

public class MovieJsonDeserializer implements JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonElement json, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Movie movie = new Movie();
        JsonElement id = jsonObject.get(ID);
        if (id != null) {
            movie.setId(id.getAsInt());
        }
        JsonElement name = jsonObject.get(NAME);
        if (name != null) {
            movie.setName(name.getAsString());
        }
        JsonElement nameOriginal = jsonObject.get(NAME_ORIGINAL);
        if (nameOriginal != null) {
            movie.setNameOriginal(nameOriginal.getAsString());
        }
        JsonElement releaseYear = jsonObject.get(NAME_ORIGINAL);
        if (releaseYear != null) {
            movie.setReleaseYear(releaseYear.getAsInt());
        }
        JsonElement description = jsonObject.get(DESCRIPTION);
        if (description != null) {
            movie.setDescription(description.getAsString());
        }
        JsonElement price = jsonObject.get(PRICE);
        if (price != null) {
            movie.setPrice(price.getAsDouble());
        }
        JsonElement countries = jsonObject.get(COUNTRIES);
        if (countries != null) {
            JsonArray countriesArray = countries.getAsJsonArray();
            List<Country> countryList = new ArrayList<>();
            for (JsonElement jsonElement : countriesArray) {
                Country country = new Country();
                country.setId(jsonElement.getAsInt());
                countryList.add(country);
            }
            movie.setCountries(countryList);
        }
        JsonElement genres = jsonObject.get(GENRES);
        if (genres != null) {
            JsonArray genresArray = genres.getAsJsonArray();
            List<Genre> genreList = new ArrayList<>();
            for (JsonElement jsonElement : genresArray) {
                Genre genre = new Genre();
                genre.setId(jsonElement.getAsInt());
                genreList.add(genre);
            }
            movie.setGenres(genreList);
        }
        return movie;
    }
}
