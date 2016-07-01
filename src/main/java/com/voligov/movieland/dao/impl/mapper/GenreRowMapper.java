package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Genre;
import com.voligov.movieland.util.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt(Constant.ID));
        if (resultSet.getMetaData().getColumnCount() > 1) {
            genre.setName(resultSet.getString(Constant.NAME));
        }
        return genre;
    }
}
