package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.enums.SortingOrder;
import com.voligov.movieland.util.gson.MovieSearchParams;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(int page, SortingOrder ratingOrder, SortingOrder priceOrder);

    Movie getById(int id);

    List<Movie> search(MovieSearchParams searchParams);

    void add(Movie movie);

    void edit(Movie movie);

    void deleteMovies(List<Integer> movies);
}
