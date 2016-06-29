package com.voligov.movieland.service;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.gson.MovieSearchParams;
import java.util.List;

public interface MovieService {

    List<Movie> getAll(String ratingOrder, String priceOrder);

    Movie getById(int id);

    List<Movie> search(MovieSearchParams searchParams);

    void add(Movie movie);

    void edit(Movie movie);
}
