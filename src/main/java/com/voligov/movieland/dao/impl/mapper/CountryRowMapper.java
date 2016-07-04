package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Country;
import com.voligov.movieland.util.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet resultSet, int i) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getInt(Constant.ID));
        if (resultSet.getMetaData().getColumnCount() > 1) {
            country.setName(resultSet.getString(Constant.NAME));
        }
        return country;
    }
}
