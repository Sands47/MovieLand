package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Rating;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingRowMapper implements RowMapper<Rating> {
    @Override
    public Rating mapRow(ResultSet resultSet, int i) throws SQLException {
        Rating rating = new Rating();
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(Constant.MOVIE_ID));
        rating.setMovie(movie);
        User user = new User();
        user.setId(resultSet.getInt(Constant.USER_ID));
        rating.setUser(user);
        rating.setRating(resultSet.getInt(Constant.RATING));
        return rating;
    }
}
