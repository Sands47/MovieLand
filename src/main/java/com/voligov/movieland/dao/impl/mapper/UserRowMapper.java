package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.User;
import com.voligov.movieland.util.Constant;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(Constant.ID));
        user.setFirstName(resultSet.getString(Constant.FIRST_NAME));
        user.setLastName(resultSet.getString(Constant.LAST_NAME));
        user.setEmail(resultSet.getString(Constant.EMAIL));
        user.setPassword(resultSet.getString(Constant.PASSWORD));
        user.setRole(UserRole.getById(resultSet.getInt(Constant.ROLE)));
        return user;
    }
}
