package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieRowMapper implements RowMapper<Movie> {

    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(Constant.ID));
        movie.setName(resultSet.getString(Constant.NAME));
        movie.setNameOriginal(resultSet.getString(Constant.NAME_ORIGINAL));
        movie.setReleaseYear(resultSet.getInt(Constant.RELEASE_YEAR));
        movie.setDescription(resultSet.getString(Constant.DESCRIPTION));
        movie.setRating(resultSet.getDouble(Constant.RATING));
        movie.setPrice(resultSet.getDouble(Constant.PRICE));
        String genres = resultSet.getString(Constant.GENRES);
        if (genres != null) {
            String[] genresArray = genres.split(Constant.COMMA);
            List<Genre> genresList = new ArrayList<>();
            for (String genreId : genresArray) {
                Genre genre = new Genre();
                genre.setId(Integer.valueOf(genreId));
                genresList.add(genre);
            }
            movie.setGenres(genresList);
        }
        String countries = resultSet.getString(Constant.COUNTRIES);
        if (countries != null) {
            String[] countriesArray = countries.split(Constant.COMMA);
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
