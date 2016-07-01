package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface CountryDao {
    List<Country> getAll();

    void addCountriesForMovie(Movie movie);

    void updateCountriesForMovie(Movie movie);

    Country getById(Integer id);
}
