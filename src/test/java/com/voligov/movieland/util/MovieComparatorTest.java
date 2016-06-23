package com.voligov.movieland.util;


import com.voligov.movieland.entity.Movie;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MovieComparatorTest {
    private static List<Movie> movies;
    private static Movie movie1;
    private static Movie movie2;
    private static Movie movie3;
    private static Movie movie4;
    private static Movie movie5;

    @BeforeClass
    public static void setUpBeforeClass() {
        movie1 = new Movie();
        movie1.setId(1);
        movie1.setRating(8.0);
        movie1.setPrice(20.0);
        movie2 = new Movie();
        movie2.setId(2);
        movie2.setRating(6.0);
        movie2.setPrice(30.0);
        movie3 = new Movie();
        movie3.setId(3);
        movie3.setRating(8.5);
        movie3.setPrice(15.0);
        movie4 = new Movie();
        movie4.setId(4);
        movie4.setRating(8.0);
        movie4.setPrice(40.0);
        movie5 = new Movie();
        movie5.setId(5);
        movie5.setRating(7.5);
        movie5.setPrice(30.0);
    }

    @Before
    public void setUp() {
        movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        movies.add(movie4);
        movies.add(movie5);
    }

    @Test
    public void testSortRatingAscPriceAsc() {
        movies.sort(new MovieComparator("asc", "asc"));
        assertEquals(movie2, movies.get(0));
        assertEquals(movie5, movies.get(1));
        assertEquals(movie1, movies.get(2));
        assertEquals(movie4, movies.get(3));
        assertEquals(movie3, movies.get(4));
    }

    @Test
    public void testSortRatingAscPriceDesc() {
        movies.sort(new MovieComparator("asc", "desc"));
        assertEquals(movie2, movies.get(0));
        assertEquals(movie5, movies.get(1));
        assertEquals(movie4, movies.get(2));
        assertEquals(movie1, movies.get(3));
        assertEquals(movie3, movies.get(4));
    }

    @Test
    public void testSortRatingDescPriceAsc() {
        movies.sort(new MovieComparator("desc", "asc"));
        assertEquals(movie3, movies.get(0));
        assertEquals(movie1, movies.get(1));
        assertEquals(movie4, movies.get(2));
        assertEquals(movie5, movies.get(3));
        assertEquals(movie2, movies.get(4));
    }

    @Test
    public void testSortRatingDescPriceDesc() {
        movies.sort(new MovieComparator("desc", "desc"));
        assertEquals(movie3, movies.get(0));
        assertEquals(movie4, movies.get(1));
        assertEquals(movie1, movies.get(2));
        assertEquals(movie5, movies.get(3));
        assertEquals(movie2, movies.get(4));
    }

    @Test
    public void testSortRatingAscPriceNull() {
        movies.sort(new MovieComparator("asc", null));
        assertEquals(movie2, movies.get(0));
        assertEquals(movie5, movies.get(1));
        assertEquals(movie1, movies.get(2));
        assertEquals(movie4, movies.get(3));
        assertEquals(movie3, movies.get(4));
    }

    @Test
    public void testSortRatingDescPriceNull() {
        movies.sort(new MovieComparator("desc", null));
        assertEquals(movie3, movies.get(0));
        assertEquals(movie1, movies.get(1));
        assertEquals(movie4, movies.get(2));
        assertEquals(movie5, movies.get(3));
        assertEquals(movie2, movies.get(4));
    }

    @Test
    public void testSortRatingNullPriceAsc() {
        movies.sort(new MovieComparator(null, "asc"));
        assertEquals(movie3, movies.get(0));
        assertEquals(movie1, movies.get(1));
        assertEquals(movie2, movies.get(2));
        assertEquals(movie5, movies.get(3));
        assertEquals(movie4, movies.get(4));
    }

    @Test
    public void testSortRatingNullPriceDesc() {
        movies.sort(new MovieComparator(null, "desc"));
        assertEquals(movie4, movies.get(0));
        assertEquals(movie2, movies.get(1));
        assertEquals(movie5, movies.get(2));
        assertEquals(movie1, movies.get(3));
        assertEquals(movie3, movies.get(4));
    }
}
