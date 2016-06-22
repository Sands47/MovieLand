package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.CountryDao;
import com.voligov.movieland.dao.impl.mapper.CountryRowMapper;
import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCountryDao implements CountryDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getCountriesSQL;

    @Autowired
    private String addMovieCountrySQL;

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
}
