package com.voligov.movieland.service.impl;

import com.voligov.movieland.caching.CountryCachingService;
import com.voligov.movieland.caching.GenreCachingService;
import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.Country;
import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.service.CountryService;
import com.voligov.movieland.service.GenreService;
import com.voligov.movieland.util.gson.MovieSearchParams;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.service.MovieService;
import com.voligov.movieland.service.ReviewService;
import com.voligov.movieland.util.MovieComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreCachingService genreCachingService;

    @Autowired
    private CountryCachingService countryCachingService;

    private static Random random = new Random();

    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = movieDao.getAll();
        for (Movie movie : movies) {
            getGenres(movie);
            getCountries(movie);
        }
        if (ratingOrder != null || priceOrder != null) {
            movies.sort(new MovieComparator(ratingOrder, priceOrder));
        }
        return movies;
    }

    @Override
    public Movie getById(int id) {
        Movie movie = movieDao.getById(id);
        if (movie != null) {
            getGenres(movie);
            getCountries(movie);
            List<Review> reviews = reviewService.getByMovieId(id);
            movie.setReviews(reviews);
            if (movie.getReviews().size() > 2) {
                List<Review> randomReviews = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    int index = random.nextInt(movie.getReviews().size());
                    randomReviews.add(movie.getReviews().get(index));
                    movie.getReviews().remove(index);
                }
                movie.setReviews(randomReviews);
            }
        }
        return movie;
    }

    @Override
    public List<Movie> search(MovieSearchParams searchParams) {
        if (searchParams.getGenre() != null) {
            Genre genre = genreCachingService.getByName(searchParams.getGenre());
            if (genre != null) {
                searchParams.setGenre(genre.getId().toString());
            }
        }
        if (searchParams.getCountry() != null) {
            Country country = countryCachingService.getByName(searchParams.getCountry());
            if (country != null) {
                searchParams.setCountry(country.getId().toString());
            }
        }
        List<Movie> movies = movieDao.search(searchParams);
        for (Movie movie : movies) {
            getGenres(movie);
            getCountries(movie);
        }
        return movies;
    }

    @Override
    public void add(Movie movie) {
        movieDao.add(movie);
        genreService.addGenresForMovie(movie);
        countryService.addCountriesForMovie(movie);
    }

    @Override
    public void edit(Movie movie) {
        movieDao.edit(movie);
        genreService.updateGenresForMovie(movie);
        countryService.updateCountriesForMovie(movie);
    }

    private void getGenres(Movie movie) {
        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres()) {
                genre.setName(genreCachingService.getById(genre.getId()).getName());
            }
        }
    }

    private void getCountries(Movie movie) {
        if (movie.getCountries() != null) {
            for (Country country : movie.getCountries()) {
                country.setName(countryCachingService.getById(country.getId()).getName());
            }
        }
    }
}
