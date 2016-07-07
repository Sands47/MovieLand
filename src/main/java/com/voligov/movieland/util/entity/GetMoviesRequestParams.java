package com.voligov.movieland.util.entity;


import com.voligov.movieland.util.enums.SortingOrder;

public class GetMoviesRequestParams {
    private SortingOrder ratingOrder;
    private SortingOrder priceOrder;
    private Integer page;
    private String currency;

    public SortingOrder getRatingOrder() {
        return ratingOrder;
    }

    public void setRatingOrder(SortingOrder ratingOrder) {
        this.ratingOrder = ratingOrder;
    }

    public SortingOrder getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(SortingOrder priceOrder) {
        this.priceOrder = priceOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
