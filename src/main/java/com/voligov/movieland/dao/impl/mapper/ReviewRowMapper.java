package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt(Constant.ID));
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(Constant.MOVIE_ID));
        review.setMovie(movie);
        User user = new User();
        user.setId(resultSet.getInt(Constant.USER_ID));
        user.setFirstName(resultSet.getString(Constant.FIRST_NAME));
        user.setLastName(resultSet.getString(Constant.LAST_NAME));
        review.setUser(user);
        review.setText(resultSet.getString(Constant.TEXT));
        return review;
    }
}
