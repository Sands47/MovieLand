package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRowMapper implements RowMapper<Movie> {

    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("id"));
        movie.setName(resultSet.getString("name"));
        movie.setNameOriginal(resultSet.getString("name_original"));
        movie.setReleaseYear(resultSet.getInt("release_year"));
        movie.setDescription(resultSet.getString("description"));
        movie.setRating(resultSet.getDouble("rating"));
        movie.setPrice(resultSet.getDouble("price"));
        String genres = resultSet.getString("genres");
        if (genres != null) {
            List<String> genresList = new ArrayList<>(Arrays.asList(genres.split(",")));
            movie.setGenres(genresList);
        }
        String countries = resultSet.getString("countries");
        if (countries != null) {
            List<String> countriesList = new ArrayList<>(Arrays.asList(countries.split(",")));
            movie.setCountries(countriesList);
        }
        return movie;
    }
}
