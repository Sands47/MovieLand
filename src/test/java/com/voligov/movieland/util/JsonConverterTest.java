package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    @Test
    public void testMovieListToJson() {
        String expectedJson = "[{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":\"Jay and Silent Bob Strike Back\",\"releaseYear\":1999,\"rating\":10.0,\"genres\":[\"Comedy\",\"Action\"]},{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":\"Jay and Silent Bob Strike Back\",\"releaseYear\":1999,\"rating\":10.0,\"genres\":[\"Comedy\",\"Action\"]}]";
        JsonConverter jsonConverter = new JsonConverter();
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Джей и Молчаливый Боб Наносят Ответный Удар");
        movie.setNameOriginal("Jay and Silent Bob Strike Back");
        movie.setReleaseYear(1999);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<String> genres = new ArrayList<String>();
        genres.add("Comedy");
        genres.add("Action");
        movie.setGenres(genres);
        List<String> countries = new ArrayList<String>();
        countries.add("USA");
        countries.add("country2");
        countries.add("country3");
        movie.setCountries(countries);
        List<Movie> movies = new ArrayList<Movie>();
        movies.add(movie);
        movies.add(movie);

        String actualJson = jsonConverter.toJson(movies);
        assertEquals(expectedJson, actualJson);
    }
}
