package com.voligov.movieland.util;

import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;

public class QueryBuilder {
    private final int PAGE_SIZE = 5;

    public String buildPagedQuery(GetMoviesRequestParams params, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if ((params.getRatingOrder() != null) || (params.getPriceOrder() != null)) {
            builder.append(Constant.ORDER_BY);
            builder.append(Constant.SPACE);
            if (params.getRatingOrder() != null) {
                builder.append(Constant.RATING);
                builder.append(Constant.SPACE);
                builder.append(params.getRatingOrder().getSortOrder());
                builder.append(Constant.COMMA);
            }
            if (params.getPriceOrder() != null) {
                builder.append(Constant.PRICE);
                builder.append(Constant.SPACE);
                builder.append(params.getPriceOrder().getSortOrder());
                builder.append(Constant.COMMA);
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append(Constant.SPACE);
        }
        builder.append(Constant.LIMIT);
        builder.append(Constant.SPACE);
        builder.append(PAGE_SIZE * (params.getPage() - 1));
        builder.append(Constant.COMMA);
        builder.append(PAGE_SIZE);
        builder.append(Constant.SEMICOLON);
        return builder.toString();
    }

    public String buildSearchQuery(MovieSearchParams searchParams, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if (!searchParams.isEmpty()) {
            builder.append(Constant.WHERE);
            builder.append(Constant.SPACE);
            addQueryCondition(Constant.GENRES, searchParams.getGenre(), builder);
            addQueryCondition(Constant.COUNTRIES, searchParams.getCountry(), builder);
            addQueryCondition(Constant.NAME, searchParams.getTitle(), builder);
            addQueryCondition(Constant.NAME_ORIGINAL, searchParams.getTitleOriginal(), builder);
            addQueryCondition(Constant.RELEASE_YEAR, searchParams.getReleaseYear(), builder);
            builder.delete(builder.length() - 5, builder.length());
        }
        return builder.toString();
    }

    private void addQueryCondition(String field, String value, StringBuilder builder) {
        if (value == null) {
            return;
        }
        builder.append(field);
        builder.append(Constant.SPACE);
        builder.append(Constant.LIKE);
        builder.append(Constant.SPACE);
        builder.append(Constant.QUOTE);
        builder.append(Constant.PERCENT);
        builder.append(value);
        builder.append(Constant.PERCENT);
        builder.append(Constant.QUOTE);
        builder.append(Constant.SPACE);
        builder.append(Constant.AND);
        builder.append(Constant.SPACE);
    }
}
