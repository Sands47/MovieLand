package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.GenreDao;
import com.voligov.movieland.dao.impl.mapper.GenreRowMapper;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcGenreDao implements GenreDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private String getGenresSQL;

    @Autowired
    private String getGenreByIdSQL;

    @Autowired
    private String addMovieGenreSQL;

    @Autowired
    private String getGenresByMovieSQL;

    @Autowired
    private String deleteGenreForMovieSQL;

    @Autowired
    private String deleteGenresForMoviesSQL;

    private final GenreRowMapper genreRowMapper = new GenreRowMapper();

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(getGenresSQL, genreRowMapper);
    }

    @Override
    public void addGenresForMovie(Movie movie) {
        for (Genre genre : movie.getGenres()) {
            jdbcTemplate.update(addMovieGenreSQL, movie.getId(), genre.getId());
        }
    }

    @Override
    public void updateGenresForMovie(Movie movie) {
        List<Genre> genres = jdbcTemplate.query(getGenresByMovieSQL, genreRowMapper, movie.getId());
        for (Genre genre : movie.getGenres()) {
            if (!genres.contains(genre)) {
                jdbcTemplate.update(addMovieGenreSQL, movie.getId(), genre.getId());
            }
        }
        for (Genre genre : genres) {
            if (!movie.getGenres().contains(genre)) {
                jdbcTemplate.update(deleteGenreForMovieSQL, movie.getId(), genre.getId());
            }
        }
    }

    @Override
    public Genre getById(Integer id) {
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(getGenreByIdSQL, genreRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return genre;
    }

    @Override
    public void deleteGenresForMovies(List<Integer> movies) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("movieIds", movies);
        namedParameterJdbcTemplate.update(deleteGenresForMoviesSQL, parameters);
    }
}
