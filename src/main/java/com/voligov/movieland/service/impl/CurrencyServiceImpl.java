package com.voligov.movieland.service.impl;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.CurrencyService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.entity.ExchangeRate;
import com.voligov.movieland.util.http.CustomHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.voligov.movieland.util.Constant.PRIVAT_NBU_API_URI;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JsonConverter jsonConverter;

    private volatile Map<String, Double> exchangeRates = new HashMap<>();

    @Override
    public void convertCurrency(Movie movie, String currency) {
        Double rate = exchangeRates.get(currency);
        if (rate != null) {
            BigDecimal convertedPrice = new BigDecimal(movie.getPrice());
            BigDecimal exchangeRate = new BigDecimal(rate);
            convertedPrice = convertedPrice.divide(exchangeRate, BigDecimal.ROUND_UP);
            movie.setPrice(convertedPrice.doubleValue());
        } else {
            log.warn("Requested currency is not supported");
        }
    }

    @Override
    public void convertCurrency(List<Movie> movies, String currency) {
        Double rate = exchangeRates.get(currency);
        if (rate != null) {
            BigDecimal exchangeRate = new BigDecimal(rate);
            for (Movie movie : movies) {
                BigDecimal convertedPrice = new BigDecimal(movie.getPrice());
                convertedPrice = convertedPrice.divide(exchangeRate, BigDecimal.ROUND_UP);
                movie.setPrice(convertedPrice.doubleValue());
            }
        } else {
            log.warn("Requested currency is not supported");
        }
    }

    @PostConstruct
    @Scheduled(cron = "0 0 6 * * *")
    public void updateExchangeRates() {
        try {
            HttpGet exchangeRatesRequest = new HttpGet(PRIVAT_NBU_API_URI);
            String exchangeRatesJson = CustomHttpClient.get(exchangeRatesRequest, 2000, 2000);
            List<ExchangeRate> exchangeRateList = jsonConverter.parseExchangeRates(exchangeRatesJson);
            Map<String, Double> newExchangeRates = new HashMap<>();
            for (ExchangeRate exchangeRate : exchangeRateList) {
                newExchangeRates.put(exchangeRate.getCurrency(), exchangeRate.getBuyRate());
            }
            exchangeRates = newExchangeRates;
        } catch (IOException e) {
            log.error("Exception getting currency exchange rates from Privatbank API", e);
        }
    }
}
