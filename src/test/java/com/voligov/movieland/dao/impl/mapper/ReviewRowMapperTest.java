package com.voligov.movieland.dao.impl.mapper;

import com.voligov.movieland.entity.Review;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewRowMapperTest {
    @Test
    public void testMapRowWithProperReview() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(any())).thenReturn(1).thenReturn(2).thenReturn(2);
        when(resultSet.getString(any())).thenReturn("Anton").thenReturn("Sosnitskiy").thenReturn("Test review");
        when(resultSet.getDouble(any())).thenReturn(10.0).thenReturn(19.99);

        ReviewRowMapper mapper = new ReviewRowMapper();
        Review review = mapper.mapRow(resultSet, 0);
        assertEquals(review.getId(), 1);
        assertEquals(review.getMovie().getId(), 2);
        assertEquals(review.getUser().getId(), 2);
        assertEquals(review.getUser().getFirstName(), "Anton");
        assertEquals(review.getUser().getLastName(), "Sosnitskiy");
        assertEquals(review.getText(), "Test review");
    }
}
