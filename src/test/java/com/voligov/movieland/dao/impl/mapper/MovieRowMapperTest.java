package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void testMapRowWithProperMovie() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyString())).thenReturn(1).thenReturn(1999);
        when(resultSet.getString(anyString())).thenReturn("Джей и Молчаливый Боб Наносят Ответный Удар").
                thenReturn("Jay and Silent Bob Strike Back").thenReturn("test").thenReturn("Comedy").thenReturn("USA");
        when(resultSet.getDouble(anyString())).thenReturn(10.0).thenReturn(19.99);

        MovieRowMapper mapper = new MovieRowMapper();
        Movie movie = mapper.mapRow(resultSet, 0);
        assertEquals(movie.getId(), 1);
        assertEquals(movie.getName(), "Джей и Молчаливый Боб Наносят Ответный Удар");
        assertEquals(movie.getNameOriginal(), "Jay and Silent Bob Strike Back");
        assertEquals(movie.getReleaseYear(), 1999);
        assertEquals(movie.getDescription(), "test");
        assertEquals(movie.getRating(), 10.0, 0.01);
        assertEquals(movie.getPrice(), 19.99, 0.01);
        assertEquals(movie.getGenres(), "Comedy");
        assertEquals(movie.getCountries(), "USA");
    }
}
