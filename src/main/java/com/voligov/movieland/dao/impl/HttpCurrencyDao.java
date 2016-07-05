package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.CurrencyDao;
import com.voligov.movieland.util.http.CustomHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;

import static com.voligov.movieland.util.Constant.PRIVAT_NBU_API_URI;

@Repository
public class HttpCurrencyDao implements CurrencyDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public String getExchangeRates() {
        String exchangeRatesJson = null;
        try {
            HttpGet exchangeRatesRequest = new HttpGet(PRIVAT_NBU_API_URI);
            exchangeRatesJson = CustomHttpClient.get(exchangeRatesRequest, 2000, 2000);
        } catch (IOException e) {
            log.error("Exception getting currency exchange rates from Privatbank API", e);
        }
        return exchangeRatesJson;
    }
}
