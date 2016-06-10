package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public Movie getById(int id) {
        return movieDao.getById(id);
    }

    @Override
    public List<Movie> search(Map<String, String> searchParams) {
        return movieDao.search(searchParams);
    }
}
