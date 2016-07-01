package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.GenreDao;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreDao genreDao;

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }

    @Override
    public void addGenresForMovie(Movie movie) {
        if (movie.getGenres() != null) {
            genreDao.addGenresForMovie(movie);
        }
    }

    @Override
    public void updateGenresForMovie(Movie movie) {
        if (movie.getGenres() != null) {
            genreDao.updateGenresForMovie(movie);
        }
    }

    @Override
    public Genre getById(Integer id) {
        return genreDao.getById(id);
    }

    @Override
    public void deleteGenresForMovies(String movies) {
        genreDao.deleteGenresForMovies(movies);
    }
}
