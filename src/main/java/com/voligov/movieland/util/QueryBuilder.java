package com.voligov.movieland.util;

import com.voligov.movieland.util.enums.SortingOrder;
import com.voligov.movieland.util.gson.MovieSearchParams;

public class QueryBuilder {
    private final int PAGE_SIZE = 5;

    public String buildPagedQuery(int page, SortingOrder ratingOrder, SortingOrder priceOrder, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if ((ratingOrder != null) || (priceOrder != null)) {
            builder.append("ORDER BY ");
            if (ratingOrder != null) {
                builder.append("rating ");
                builder.append(ratingOrder.getSortOrder());
                builder.append(",");
            }
            if (priceOrder != null) {
                builder.append("price ");
                builder.append(priceOrder.getSortOrder());
                builder.append(",");
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append(" ");
        }
        builder.append("LIMIT ");
        builder.append(PAGE_SIZE * (page - 1));
        builder.append(",");
        builder.append(PAGE_SIZE);
        builder.append(";");
        return builder.toString();
    }

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
