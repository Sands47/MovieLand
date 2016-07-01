package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.CountryDao;
import com.voligov.movieland.dao.impl.mapper.CountryRowMapper;
import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private String getCountriesSQL;

    @Autowired
    private String getCountryByIdSQL;

    @Autowired
    private String addMovieCountrySQL;

    @Autowired
    private String getCountriesByMovieSQL;

    @Autowired
    private String deleteCountryForMovieSQL;

    @Autowired
    private String deleteCountriesForMoviesSQL;

    private final CountryRowMapper countryRowMapper = new CountryRowMapper();

    @Override
    public List<Country> getAll() {
        return jdbcTemplate.query(getCountriesSQL, countryRowMapper);
    }

    @Override
    public void addCountriesForMovie(Movie movie) {
        for (Country country : movie.getCountries()) {
            jdbcTemplate.update(addMovieCountrySQL, movie.getId(), country.getId());
        }
    }

    @Override
    public void updateCountriesForMovie(Movie movie) {
        List<Country> countries = jdbcTemplate.query(getCountriesByMovieSQL, countryRowMapper, movie.getId());
        for (Country country : movie.getCountries()) {
            if (!countries.contains(country)) {
                jdbcTemplate.update(addMovieCountrySQL, movie.getId(), country.getId());
            }
        }
        for (Country country : countries) {
            if (!movie.getCountries().contains(country)) {
                jdbcTemplate.update(deleteCountryForMovieSQL, movie.getId(), country.getId());
            }
        }
    }

    @Override
    public Country getById(Integer id) {
        Country country;
        try {
            country = jdbcTemplate.queryForObject(getCountryByIdSQL, countryRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return country;
    }

    @Override
    public void deleteCountriesForMovies(List<Integer> movies) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieIds", movies);
        namedParameterJdbcTemplate.update(deleteCountriesForMoviesSQL, parameters);
    }
}
