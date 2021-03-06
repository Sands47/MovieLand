package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void testMapRowWithProperMovie() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(1).thenReturn(1999);
        when(resultSet.getString(any())).thenReturn("Джей и Молчаливый Боб Наносят Ответный Удар").
                thenReturn("Jay and Silent Bob Strike Back").thenReturn("test").thenReturn("1,2").thenReturn("3,4");
        when(resultSet.getDouble(any())).thenReturn(10.0).thenReturn(20.0);

        MovieRowMapper mapper = new MovieRowMapper();
        Movie movie = mapper.mapRow(resultSet, 0);

        List<Genre> expectedGenres = new ArrayList<>();
        Genre genre = new Genre();
        genre.setId(1);
        expectedGenres.add(genre);
        genre = new Genre();
        genre.setId(2);
        expectedGenres.add(genre);
        List<Country> expectedCountries = new ArrayList<>();
        Country country = new Country();
        country.setId(3);
        expectedCountries.add(country);
        country = new Country();
        country.setId(4);
        expectedCountries.add(country);
        assertEquals(movie.getId().intValue(), 1);
        assertEquals(movie.getName(), "Джей и Молчаливый Боб Наносят Ответный Удар");
        assertEquals(movie.getNameOriginal(), "Jay and Silent Bob Strike Back");
        assertEquals(movie.getReleaseYear().intValue(), 1999);
        assertEquals(movie.getDescription(), "test");
        assertEquals(movie.getRating(), 10.0, 0.01);
        assertEquals(movie.getPrice(), 20, 0.01);
        assertEquals(movie.getGenres(), expectedGenres);
        assertEquals(movie.getCountries(), expectedCountries);
    }
}
