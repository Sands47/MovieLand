package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.gson.MovieSearchParams;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll();

    Movie getById(int id);

    List<Movie> search(MovieSearchParams searchParams);
}
