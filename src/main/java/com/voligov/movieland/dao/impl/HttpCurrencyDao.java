package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.CurrencyDao;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.entity.ExchangeRate;
import com.voligov.movieland.util.http.CustomHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.voligov.movieland.util.Constant.PRIVAT_NBU_API_URI;

@Repository
public class HttpCurrencyDao implements CurrencyDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JsonConverter jsonConverter;

    private Map<String, Double> exchangeRates = new ConcurrentHashMap<>();

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * *")
    public void updateExchangeRates() {
        try {
            HttpGet exchangeRatesRequest = new HttpGet(PRIVAT_NBU_API_URI);
            String exchangeRatesJson = CustomHttpClient.get(exchangeRatesRequest, 2000, 2000);
            List<ExchangeRate> exchangeRateList = jsonConverter.parseExchangeRates(exchangeRatesJson);
            for (ExchangeRate exchangeRate : exchangeRateList) {
                exchangeRates.put(exchangeRate.getCurrency(), exchangeRate.getBuyRate());
            }
        } catch (IOException e) {
            log.error("Exception getting currency exchange rates from Privatbank API", e);
        }
    }

    @Override
    public Double getExchangeRate(String currency) {
        return exchangeRates.get(currency);
    }
}
