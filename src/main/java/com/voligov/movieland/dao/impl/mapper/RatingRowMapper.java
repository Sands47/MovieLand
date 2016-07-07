package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.voligov.movieland.util.Constant.*;

public class RatingRowMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(MOVIE_ID));
        rating.setMovie(movie);
        User user = new User();
        user.setId(resultSet.getInt(USER_ID));
        rating.setUser(user);
        rating.setRating(resultSet.getInt(RATING));
        return rating;
    }
}
