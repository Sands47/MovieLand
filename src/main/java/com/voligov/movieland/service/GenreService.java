package com.voligov.movieland.service;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    void addGenresForMovie(Movie movie);
}
