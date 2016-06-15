package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

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
                thenReturn("Jay and Silent Bob Strike Back").thenReturn("test").thenReturn("1,2").thenReturn("USA,Country2");
        when(resultSet.getDouble(any())).thenReturn(10.0).thenReturn(20.0);

        MovieRowMapper mapper = new MovieRowMapper();
        Movie movie = mapper.mapRow(resultSet, 0);
        assertEquals(movie.getId().intValue(), 1);
        assertEquals(movie.getName(), "Джей и Молчаливый Боб Наносят Ответный Удар");
        assertEquals(movie.getNameOriginal(), "Jay and Silent Bob Strike Back");
        assertEquals(movie.getReleaseYear().intValue(), 1999);
        assertEquals(movie.getDescription(), "test");
        assertEquals(movie.getRating(), 10.0, 0.01);
        assertEquals(movie.getPrice(), 20, 0.01);
        assertEquals(movie.getGenreIds(), "1,2");
        assertEquals(movie.getCountries(), Arrays.asList("USA", "Country2"));
    }
}
