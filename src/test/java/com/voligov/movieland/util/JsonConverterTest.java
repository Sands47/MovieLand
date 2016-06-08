package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    @Test
    public void testCityToJson() {
        String expectedJson = "\"movies\":[\n" +
                "{\"name\":\"name\",\"name_original\":\"name_original\",\"release_year\":\"1990\",\"description\":\"description\",\"rating\":\"10.0\",\"price\":\"20.0\",\"genres\":\"[genre]\",\"countries\":\"[country]\"}\n" +
                "]";
        JsonConverter jsonConverter = new JsonConverter();
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("name");
        movie.setNameOriginal("name_original");
        movie.setReleaseYear(1990);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<String> genres = new ArrayList<String>();
        genres.add("genre");
        movie.setGenres(genres);
        List<String> countries = new ArrayList<String>();
        countries.add("country");
        movie.setCountries(countries);
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(movie);

        String actualJson = jsonConverter.toJson(movies);
        assertEquals(expectedJson, actualJson);
    }
}
