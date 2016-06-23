package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.CountryDao;
import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAll() {
        return countryDao.getAll();
    }

    @Override
    public void addCountriesForMovie(Movie movie) {
        if (movie.getCountries() != null) {
            countryDao.addCountriesForMovie(movie);
        }
    }
}
