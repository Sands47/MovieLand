package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface CurrencyService {
    void convertCurrency(Movie movie, String currency);

    void convertCurrency(List<Movie> movies, String currency);
}
