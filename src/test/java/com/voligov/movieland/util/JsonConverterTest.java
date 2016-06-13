package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.MovieSearchParams;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private static JsonConverter jsonConverter;

    @BeforeClass
    public static void setUp() {
        jsonConverter = new JsonConverter();
    }

    @Test
    public void testMovieListToJson() {
        String expectedJson = "[{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":\"Jay and Silent Bob Strike Back\"," +
                "\"releaseYear\":1999,\"rating\":10.0,\"genres\":[\"Comedy\",\"Action\"]},{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\"," +
                "\"nameOriginal\":\"Jay and Silent Bob Strike Back\",\"releaseYear\":1999,\"rating\":10.0,\"genres\":[\"Comedy\",\"Action\"]}]";
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Джей и Молчаливый Боб Наносят Ответный Удар");
        movie.setNameOriginal("Jay and Silent Bob Strike Back");
        movie.setReleaseYear(1999);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<String> genres = Arrays.asList("Comedy", "Action");
        movie.setGenres(genres);
        List<String> countries = Arrays.asList("USA", "country2", "country3");
        movie.setCountries(countries);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie);

        String actualJson = jsonConverter.toJson(movies);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testMovieToJson() {
        String expectedJson = "{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":\"Jay and Silent Bob Strike Back\"," +
                "\"releaseYear\":1999,\"description\":\"description\",\"rating\":10.0,\"genres\":[\"Comedy\",\"Action\"],\"countries\":[\"USA\"," +
                "\"country2\",\"country3\"],\"reviews\":[{\"user\":{\"firstName\":\"Anton\",\"lastName\":\"Sosnitskiy\"},\"text\":\"Test review\"}," +
                "{\"user\":{\"firstName\":\"Anton\",\"lastName\":\"Sosnitskiy\"},\"text\":\"Test review\"}]}";
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Джей и Молчаливый Боб Наносят Ответный Удар");
        movie.setNameOriginal("Jay and Silent Bob Strike Back");
        movie.setReleaseYear(1999);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<String> genres = Arrays.asList("Comedy", "Action");
        movie.setGenres(genres);
        List<String> countries = Arrays.asList("USA", "country2", "country3");
        movie.setCountries(countries);
        Review review = new Review();
        review.setId(1);
        review.setMovie(movie);
        User user = new User();
        user.setId(1);
        user.setFirstName("Anton");
        user.setLastName("Sosnitskiy");
        review.setUser(user);
        review.setText("Test review");
        List<Review> reviews = Arrays.asList(review, review);
        movie.setReviews(reviews);

        String actualJson = jsonConverter.toJson(movie);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testParseSearchParams() {
        String json = "{\"genre\": \"комедия\", \"country\": \"США\", \"title\": \"Test\", \"titleOriginal\": \"Test\", \"releaseYear\": \"1999\"}";
        MovieSearchParams params = jsonConverter.parseSearchParams(json);
        assertEquals(params.getGenre(), "комедия");
        assertEquals(params.getCountry(), "США");
        assertEquals(params.getTitle(), "Test");
        assertEquals(params.getTitleOriginal(), "Test");
        assertEquals(params.getReleaseYear(), "1999");
    }
}
