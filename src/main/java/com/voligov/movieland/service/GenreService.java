package com.voligov.movieland.service;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    void addGenresForMovie(Movie movie);

    void updateGenresForMovie(Movie movie);

    Genre getById(Integer id);

    void deleteGenresForMovies(List<Integer> movies);
}
