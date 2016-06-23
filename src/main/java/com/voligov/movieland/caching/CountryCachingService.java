package com.voligov.movieland.caching;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CountryCachingService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private List<Country> countryCache = new CopyOnWriteArrayList<>();

    @Autowired
    private CountryService countryService;

    public Country getById(int id) {
        for (Country country : countryCache) {
            if (country.getId() == id) {
                return country;
            }
        }
        log.warn("Country with id = {} not found in cache", id);
        return null;
    }

    public Country getByName(String name) {
        for (Country country : countryCache) {
            if (country.getName().equals(name)) {
                return country;
            }
        }
        log.warn("Country with name = {} not found in cache", name);
        return null;
    }

    @Scheduled(fixedRate = 4 * 60 * 60 * 1000)
    public void updateCountryCache() {
        List<Country> countriesFromDb = countryService.getAll();
        if (countryCache.isEmpty()) {
            countryCache.addAll(countriesFromDb);
        } else {
            for (Country country : countriesFromDb) {
                if (!countryCache.contains(country)) {
                    countryCache.add(country);
                }
            }
        }
    }
}
