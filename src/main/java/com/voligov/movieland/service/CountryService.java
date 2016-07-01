package com.voligov.movieland.service;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface CountryService {
    List<Country> getAll();

    void addCountriesForMovie(Movie movie);

    void updateCountriesForMovie(Movie movie);

    Country getById(Integer id);

    void deleteCountriesForMovies(String movies);
}
