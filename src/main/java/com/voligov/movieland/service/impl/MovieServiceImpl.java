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
        }
        return movies;
    }

    @Override
    public void add(Movie movie) {
        movieDao.add(movie);
        if (movie.getGenreIds() != null) {
            List<Genre> genresList = new ArrayList<>();
            String[] genresSplit = movie.getGenreIds().split(",");
            for (String genreName : genresSplit) {
                Genre genre = genreCachingService.getByName(genreName);
                if (genre != null) {
                    genresList.add(genre);
                }
            }
            movie.setGenres(genresList);
            genreService.addGenresForMovie(movie);
        }
        if (movie.getCountryIds() != null) {
            List<Country> countriesList = new ArrayList<>();
            String[] countriesSplit = movie.getCountryIds().split(",");
            for (String countryName : countriesSplit) {
                Country country = countryCachingService.getByName(countryName);
                if (country != null) {
                    countriesList.add(country);
                }
            }
            movie.setCountries(countriesList);
            countryService.addCountriesForMovie(movie);
        }
    }

    private void getGenres(Movie movie) {
        if (movie.getGenreIds() != null) {
            String[] genres = movie.getGenreIds().split(",");
            List<Genre> genresList = new ArrayList<>();
            for (String genre : genres) {
                genresList.add(genreCachingService.getById(Integer.valueOf(genre)));
            }
            movie.setGenres(genresList);
        }
    }

    private void getCountries(Movie movie) {
        if (movie.getCountryIds() != null) {
            String[] countries = movie.getCountryIds().split(",");
            List<Country> countriesList = new ArrayList<>();
            for (String country : countries) {
                countriesList.add(countryCachingService.getById(Integer.valueOf(country)));
            }
            movie.setCountries(countriesList);
        }
    }
}
