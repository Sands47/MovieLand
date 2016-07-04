package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.CurrencyDao;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyDao currencyDao;

    @Override
    public void convertCurrency(Movie movie, String currency) {
        if (currency != null) {
            Double rate = currencyDao.getExchangeRate(currency);
            if (rate != null) {
                BigDecimal convertedPrice = new BigDecimal(movie.getPrice());
                BigDecimal exchangeRate = new BigDecimal(rate);
                convertedPrice = convertedPrice.divide(exchangeRate, BigDecimal.ROUND_UP);
                movie.setPrice(convertedPrice.doubleValue());
            }
        }
    }
}
