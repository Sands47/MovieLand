package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.User;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    public void testMapRowWithProperUser() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(1);
        when(resultSet.getString(any())).thenReturn("Anton").thenReturn("Sosnitskiy").
                thenReturn("test@email.com").thenReturn("pwd");

        UserRowMapper mapper = new UserRowMapper();
        User user = mapper.mapRow(resultSet, 0);
        assertEquals(user.getId().intValue(), 1);
        assertEquals(user.getFirstName(), "Anton");
        assertEquals(user.getLastName(), "Sosnitskiy");
        assertEquals(user.getEmail(), "test@email.com");
        assertEquals(user.getPassword(), "pwd");
    }
}
