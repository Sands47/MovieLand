package com.voligov.movieland.util;


import com.voligov.movieland.util.enums.SortingOrder;

public class GetMoviesRequestParams {
    private SortingOrder ratingOrder;
    private SortingOrder priceOrder;
    Integer page;

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
}
