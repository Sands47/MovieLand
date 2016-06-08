package com.voligov.movieland.dao;

import com.voligov.movieland.entity.Movie;
import java.util.List;

public interface MovieDao {
    List<Movie> getAll();
}
