package com.voligov.movieland.util;

import com.voligov.movieland.entity.*;
import com.voligov.movieland.util.entity.MovieSearchParams;
import com.voligov.movieland.util.entity.UserCredentials;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonConverterTest {
    private JsonConverter jsonConverter = new JsonConverter();

    @Test
    public void testMovieListToJson() {
        String expectedJson = "[{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":\"Jay and Silent Bob Strike Back\"," +
                "\"releaseYear\":1999,\"rating\":10.0,\"genres\":[{\"name\":\"Comedy\"},{\"name\":\"Action\"}]},{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\"," +
                "\"nameOriginal\":\"Jay and Silent Bob Strike Back\",\"releaseYear\":1999,\"rating\":10.0,\"genres\":[{\"name\":\"Comedy\"},{\"name\":\"Action\"}]}]";
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Джей и Молчаливый Боб Наносят Ответный Удар");
        movie.setNameOriginal("Jay and Silent Bob Strike Back");
        movie.setReleaseYear(1999);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setName("Comedy");
        genres.add(genre);
        genre = new Genre();
        genre.setName("Action");
        genres.add(genre);
        movie.setGenres(genres);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie);

        String actualJson = jsonConverter.toJson(movies);
        assertEquals(expectedJson, actualJson);
    }

    @Test
    public void testMovieToJson() {
        String expectedJson = "{\"name\":\"Джей и Молчаливый Боб Наносят Ответный Удар\",\"nameOriginal\":" +
                "\"Jay and Silent Bob Strike Back\",\"releaseYear\":1999,\"description\":\"description\",\"rating\":10.0," +
                "\"genres\":[{\"name\":\"Comedy\"},{\"name\":\"Action\"}],\"countries\":[{\"name\":\"USA\"},{\"name\":\"Test\"}]," +
                "\"reviews\":[{\"user\":{\"firstName\":\"Anton\",\"lastName\":\"Sosnitskiy\"},\"text\":\"Test review\"},{\"user\":{" +
                "\"firstName\":\"Anton\",\"lastName\":\"Sosnitskiy\"},\"text\":\"Test review\"}]}";
        Movie movie = new Movie();
        movie.setId(1);
        movie.setName("Джей и Молчаливый Боб Наносят Ответный Удар");
        movie.setNameOriginal("Jay and Silent Bob Strike Back");
        movie.setReleaseYear(1999);
        movie.setDescription("description");
        movie.setRating(10.0);
        movie.setPrice(20.0);
        List<Genre> genres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setName("Comedy");
        genres.add(genre);
        genre = new Genre();
        genre.setName("Action");
        genres.add(genre);
        movie.setGenres(genres);
        List<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.setName("USA");
        countries.add(country);
        country = new Country();
        country.setName("Test");
        countries.add(country);
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

    @Test
    public void testParseUserCredentials() {
        String json = "{\"login\": \"user@email.com\", \"password\": \"pwd\"}";
        UserCredentials userCredentials = jsonConverter.parseUserCredentials(json);
        assertEquals(userCredentials.getLogin(), "user@email.com");
        assertEquals(userCredentials.getPassword(), "pwd");
    }

    @Test
    public void testParseReview() {
        String json = "{\"movie_id\": \"1\", \"user_id\": \"2\", \"text\": \"Test review\"}";
        Review review = jsonConverter.parseReview(json);
        assertEquals(review.getMovie().getId().toString(), "1");
        assertEquals(review.getUser().getId().toString(), "2");
        assertEquals(review.getText(), "Test review");
    }

    @Test
    public void testParseRating() {
        String json = "{\"movie_id\": \"1\",\"rating\": \"10\"}";
        Rating rating = jsonConverter.parseRating(json);
        assertEquals(rating.getRating().toString(), "10");
        assertEquals(rating.getMovie().getId().toString(), "1");
    }
}
