package com.voligov.movieland.util;

import com.voligov.movieland.entity.MovieSearchParams;

public class QueryBuilder {

    public String buildSearchQuery(MovieSearchParams searchParams, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if (!searchParams.isEmpty()) {
            builder.append("WHERE ");
            builder.append(addQueryCondition("genres", searchParams.getGenre()));
            builder.append(addQueryCondition("countries", searchParams.getCountry()));
            builder.append(addQueryCondition("name", searchParams.getTitle()));
            builder.append(addQueryCondition("name_original", searchParams.getTitleOriginal()));
            builder.append(addQueryCondition("release_year", searchParams.getReleaseYear()));
            builder.delete(builder.length() - 5, builder.length());
        }
        return builder.toString();
    }

    private String addQueryCondition(String field, String value) {
        if (value == null) {
            return "";
        }
        return field + " LIKE '%" + value + "%' AND ";
    }
}
