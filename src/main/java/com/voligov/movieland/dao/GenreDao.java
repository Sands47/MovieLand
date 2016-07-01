package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    void addGenresForMovie(Movie movie);

    void updateGenresForMovie(Movie movie);

    Genre getById(Integer id);
}
