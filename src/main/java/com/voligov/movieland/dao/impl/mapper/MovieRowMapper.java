package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.voligov.movieland.util.Constant.*;

public class MovieRowMapper implements RowMapper<Movie> {

    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(ID));
        movie.setName(resultSet.getString(NAME));
        movie.setNameOriginal(resultSet.getString(NAME_ORIGINAL));
        movie.setReleaseYear(resultSet.getInt(RELEASE_YEAR));
        movie.setDescription(resultSet.getString(DESCRIPTION));
        movie.setRating(resultSet.getDouble(RATING));
        movie.setPrice(resultSet.getDouble(PRICE));
        String genres = resultSet.getString(GENRES);
        if (genres != null) {
            String[] genresArray = genres.split(COMMA);
            List<Genre> genresList = new ArrayList<>();
            for (String genreId : genresArray) {
                Genre genre = new Genre();
                genre.setId(Integer.valueOf(genreId));
                genresList.add(genre);
            }
            movie.setGenres(genresList);
        }
        String countries = resultSet.getString(COUNTRIES);
        if (countries != null) {
            String[] countriesArray = countries.split(COMMA);
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
