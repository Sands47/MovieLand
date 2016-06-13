package com.voligov.movieland.service.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.MovieSearchParams;
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

    private static Random random = new Random();

    public List<Movie> getAll(String ratingOrder, String priceOrder) {
        List<Movie> movies = movieDao.getAll();
        if (ratingOrder != null || priceOrder != null) {
            movies.sort(new MovieComparator(ratingOrder, priceOrder));
        }
        return movies;
    }

    @Override
    public Movie getById(int id) {
        Movie movie = movieDao.getById(id);
        if (movie != null) {
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
        return movieDao.search(searchParams);
    }
}
