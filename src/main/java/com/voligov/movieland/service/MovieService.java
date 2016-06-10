package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;

import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getAll(String ratingOrder, String priceOrder);

    Movie getById(int id);

    List<Movie> search(Map<String, String> searchParams);
}
