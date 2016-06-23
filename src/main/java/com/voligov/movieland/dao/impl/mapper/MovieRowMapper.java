package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            String[] genresArray = genres.split(",");
            List<Genre> genresList = new ArrayList<>();
            for (String genreId : genresArray) {
                Genre genre = new Genre();
                genre.setId(Integer.valueOf(genreId));
                genresList.add(genre);
            }
            movie.setGenres(genresList);
        }
        String countries = resultSet.getString("countries");
        if (countries != null) {
            String[] countriesArray = countries.split(",");
            List<Country> countriesList = new ArrayList<>();
            for (String countryId : countriesArray) {
                Country country = new Country();
                country.setId(Integer.valueOf(countryId));
                countriesList.add(country);
            }
            movie.setCountries(countriesList);
        }
        return movie;
    }
}
