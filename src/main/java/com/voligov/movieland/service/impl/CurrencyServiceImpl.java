package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.CurrencyDao;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.CurrencyService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.entity.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Autowired
    private CurrencyDao currencyDao;

    @Autowired
    private JsonConverter jsonConverter;

    private Map<String, Double> exchangeRates = new ConcurrentHashMap<>();

    @Override
    public void convertCurrency(Movie movie, String currency) {
        if (currency != null) {
            Double rate = exchangeRates.get(currency);
            if (rate != null) {
                BigDecimal convertedPrice = new BigDecimal(movie.getPrice());
                BigDecimal exchangeRate = new BigDecimal(rate);
                convertedPrice = convertedPrice.divide(exchangeRate, BigDecimal.ROUND_UP);
                movie.setPrice(convertedPrice.doubleValue());
            }
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 6 * * *")
    public void updateExchangeRates() {
        String exchangeRatesJson = currencyDao.getExchangeRates();
        List<ExchangeRate> exchangeRateList = jsonConverter.parseExchangeRates(exchangeRatesJson);
        for (ExchangeRate exchangeRate : exchangeRateList) {
            exchangeRates.put(exchangeRate.getCurrency(), exchangeRate.getBuyRate());
        }
    }
}
