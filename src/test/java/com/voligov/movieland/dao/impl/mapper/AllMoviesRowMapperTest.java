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

public class AllMoviesRowMapperTest {

    @Test
    public void testMapRowWithMovie() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(1).thenReturn(1999);
        when(resultSet.getString(any())).thenReturn("Джей и Молчаливый Боб Наносят Ответный Удар").
                thenReturn("Jay and Silent Bob Strike Back").thenReturn("Comedy,Action");
        when(resultSet.getDouble(any())).thenReturn(10.0);

        AllMoviesRowMapper mapper = new AllMoviesRowMapper();
        Movie movie = mapper.mapRow(resultSet, 0);
        assertEquals(movie.getId(), 1);
        assertEquals(movie.getName(), "Джей и Молчаливый Боб Наносят Ответный Удар");
        assertEquals(movie.getNameOriginal(), "Jay and Silent Bob Strike Back");
        assertEquals(movie.getReleaseYear(), 1999);
        assertEquals(movie.getRating(), 10.0, 0.01);
        assertEquals(movie.getGenres(), Arrays.asList("Comedy","Action"));
    }
}
