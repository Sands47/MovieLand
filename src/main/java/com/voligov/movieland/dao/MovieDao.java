package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;

import java.util.List;

public interface MovieDao {
    List<Movie> getAll(GetMoviesRequestParams params);

    Movie getById(int id);

    List<Movie> search(MovieSearchParams searchParams);

    void add(Movie movie);

    void edit(Movie movie);

    void deleteMovies(List<Integer> movies);
}
