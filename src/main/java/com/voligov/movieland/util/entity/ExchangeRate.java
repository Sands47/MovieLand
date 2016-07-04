package com.voligov.movieland.util.entity;

import com.google.gson.annotations.SerializedName;

public class ExchangeRate {
    @SerializedName("ccy")
    private String currency;

    @SerializedName("base_ccy")
    private String baseCurrency;

    @SerializedName("buy")
    private double buyRate;

    @SerializedName("sale")
    private double sellRate;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "currency='" + currency + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", buyRate=" + buyRate +
                ", sellRate=" + sellRate +
                '}';
    }
}
