package com.voligov.movieland.service.impl;

import com.voligov.movieland.caching.CountryCachingService;
import com.voligov.movieland.caching.GenreCachingService;
import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.*;
import com.voligov.movieland.service.*;
import com.voligov.movieland.util.entity.GetMovieByIdRequestParams;
import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private GenreCachingService genreCachingService;

    @Autowired
    private CountryCachingService countryCachingService;

    @Autowired
    private CurrencyService currencyService;

    private static final Random RANDOM = new Random();

    private List<Integer> markedMovies = new ArrayList<>();

    @Override
    public List<Movie> getAll(GetMoviesRequestParams params) {
        List<Movie> movies = movieDao.getAll(params);
        for (Movie movie : movies) {
            enrichGenres(movie);
            enrichCountries(movie);
        }
        if (params.getCurrency() != null) {
            currencyService.convertCurrency(movies, params.getCurrency());
        }
        return movies;
    }

    @Override
    public Movie getById(GetMovieByIdRequestParams params) {
        Movie movie = movieDao.getById(params);
        if (movie != null) {
            enrichGenres(movie);
            enrichCountries(movie);
            if (params.getCurrency() != null) {
                currencyService.convertCurrency(movie, params.getCurrency());
            }
            List<Review> reviews = reviewService.getByMovieId(params.getMovieId());
            movie.setReviews(reviews);
            if (movie.getReviews().size() > 2) {
                List<Review> randomReviews = new ArrayList<>();
                for (int i = 0; i < 2; i++) {
                    int index = RANDOM.nextInt(movie.getReviews().size());
                    randomReviews.add(movie.getReviews().get(index));
                    movie.getReviews().remove(index);
                }
                movie.setReviews(randomReviews);
            }
            if (params.getUser() != null) {
                Rating rating = ratingService.getRatingForMovie(movie, params.getUser());
                if (rating != null) {
                    rating.setUser(params.getUser());
                    List<Rating> ratings = new ArrayList<>();
                    ratings.add(rating);
                    movie.setRatings(ratings);
                }
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
            enrichGenres(movie);
            enrichCountries(movie);
        }
        return movies;
    }

    @Override
    @Transactional
    public void add(Movie movie) {
        movieDao.add(movie);
        genreService.addGenresForMovie(movie);
        countryService.addCountriesForMovie(movie);
    }

    @Override
    @Transactional
    public void edit(Movie movie) {
        movieDao.edit(movie);
        genreService.updateGenresForMovie(movie);
        countryService.updateCountriesForMovie(movie);
    }

    @Override
    public synchronized void markForDeletion(int movieId) {
        if (!markedMovies.contains(movieId)) {
            markedMovies.add(movieId);
            log.info("Movie {} marked for deletion", movieId);
        }
    }

    @Override
    public synchronized void unmarkForDeletion(int movieId) {
        markedMovies.remove(movieId);
        log.info("Movie {} unmarked for deletion", movieId);
    }

    @Override
    public byte[] getPoster(int movieId) {
        return movieDao.getPoster(movieId);
    }

    private void enrichGenres(Movie movie) {
        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres()) {
                Genre cachedGenre = genreCachingService.getById(genre.getId());
                if (cachedGenre != null) {
                    genre.setName(cachedGenre.getName());
                } else {
                    Genre genreFromDb = genreService.getById(genre.getId());
                    genre.setName(genreFromDb.getName());
                }
            }
        }
    }

    private void enrichCountries(Movie movie) {
        if (movie.getCountries() != null) {
            for (Country country : movie.getCountries()) {
                Country cachedCountry = countryCachingService.getById(country.getId());
                if (cachedCountry != null) {
                    country.setName(cachedCountry.getName());
                } else {
                    Country countryFromDb = countryService.getById(country.getId());
                    country.setName(countryFromDb.getName());
                }
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public synchronized void deleteMarkedMovies() {
        log.info("Deleting marked movies");
        if (!markedMovies.isEmpty()) {
            genreService.deleteGenresForMovies(markedMovies);
            countryService.deleteCountriesForMovies(markedMovies);
            reviewService.deleteReviewsForMovies(markedMovies);
            movieDao.deleteMovies(markedMovies);
            log.info("Marked movies deleted, count = {}", markedMovies.size());
            markedMovies.clear();
        } else {
            log.info("No movies are marked for deletion");
        }
    }
}
