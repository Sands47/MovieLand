package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.Review;
import com.voligov.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.voligov.movieland.util.Constant.*;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt(ID));
        Movie movie = new Movie();
        movie.setId(resultSet.getInt(MOVIE_ID));
        review.setMovie(movie);
        User user = new User();
        user.setId(resultSet.getInt(USER_ID));
        user.setFirstName(resultSet.getString(FIRST_NAME));
        user.setLastName(resultSet.getString(LAST_NAME));
        review.setUser(user);
        review.setText(resultSet.getString(TEXT));
        return review;
    }
}
