package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    void addGenresForMovie(Movie movie);
}
