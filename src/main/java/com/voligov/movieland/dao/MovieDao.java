package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import java.util.List;
import java.util.Map;

public interface MovieDao {
    List<Movie> getAll();

    Movie getById(int id);

    List<Movie> search(Map<String, String> searchParams);
}
