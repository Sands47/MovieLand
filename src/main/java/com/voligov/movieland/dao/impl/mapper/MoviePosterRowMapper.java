package com.voligov.movieland.dao.impl.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoviePosterRowMapper implements RowMapper<byte[]> {
    private final LobHandler lobHandler = new DefaultLobHandler();

    @Override
    public byte[] mapRow(ResultSet resultSet, int i) throws SQLException {
        return lobHandler.getBlobAsBytes(resultSet, "poster");
    }
}
