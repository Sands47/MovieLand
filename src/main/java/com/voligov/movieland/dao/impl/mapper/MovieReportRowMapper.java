package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.dto.MovieReportDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.voligov.movieland.util.Constant.*;

public class MovieReportRowMapper implements RowMapper<MovieReportDto> {

    public MovieReportDto mapRow(ResultSet resultSet, int i) throws SQLException {
        MovieReportDto movie = new MovieReportDto();
        movie.setId(resultSet.getInt(ID));
        movie.setName(resultSet.getString(NAME));
        movie.setNameOriginal(resultSet.getString(NAME_ORIGINAL));
        movie.setReleaseYear(resultSet.getInt(RELEASE_YEAR));
        movie.setDescription(resultSet.getString(DESCRIPTION));
        movie.setRating(resultSet.getDouble(RATING));
        movie.setPrice(resultSet.getDouble(PRICE));
        movie.setGenres(resultSet.getString(GENRES));
        movie.setAddedTimestamp(resultSet.getTimestamp(ADDED_TIMESTAMP));
        movie.setUpdatedTimestamp(resultSet.getTimestamp(UPDATED_TIMESTAMP));
        movie.setReviewCount(resultSet.getInt(REVIEW_COUNT));
        return movie;
    }
}
