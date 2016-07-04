package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;

public interface CurrencyService {
    void convertCurrency(Movie movie, String currency);
}
