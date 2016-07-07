package com.voligov.movieland.util;

import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;

import static com.voligov.movieland.util.Constant.*;

public class QueryBuilder {
    private final int PAGE_SIZE = 5;

    public String buildPagedQuery(GetMoviesRequestParams params, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if ((params.getRatingOrder() != null) || (params.getPriceOrder() != null)) {
            builder.append(ORDER_BY);
            builder.append(SPACE);
            if (params.getRatingOrder() != null) {
                builder.append(RATING);
                builder.append(SPACE);
                builder.append(params.getRatingOrder().getSortOrder());
                builder.append(COMMA);
            }
            if (params.getPriceOrder() != null) {
                builder.append(PRICE);
                builder.append(SPACE);
                builder.append(params.getPriceOrder().getSortOrder());
                builder.append(COMMA);
            }
            builder.delete(builder.length() - 1, builder.length());
            builder.append(SPACE);
        }
        builder.append(LIMIT);
        builder.append(SPACE);
        builder.append(PAGE_SIZE * (params.getPage() - 1));
        builder.append(COMMA);
        builder.append(PAGE_SIZE);
        builder.append(SEMICOLON);
        return builder.toString();
    }

    public String buildSearchQuery(MovieSearchParams searchParams, String getAllMoviesSQL) {
        StringBuilder builder = new StringBuilder(getAllMoviesSQL);
        if (!searchParams.isEmpty()) {
            builder.append(WHERE);
            builder.append(SPACE);
            addQueryCondition(GENRES, searchParams.getGenre(), builder);
            addQueryCondition(COUNTRIES, searchParams.getCountry(), builder);
            addQueryCondition(NAME, searchParams.getTitle(), builder);
            addQueryCondition(NAME_ORIGINAL, searchParams.getTitleOriginal(), builder);
            addQueryCondition(RELEASE_YEAR, searchParams.getReleaseYear(), builder);
            builder.delete(builder.length() - 5, builder.length());
        }
        return builder.toString();
    }

    private void addQueryCondition(String field, String value, StringBuilder builder) {
        if (value == null) {
            return;
        }
        builder.append(field);
        builder.append(SPACE);
        builder.append(LIKE);
        builder.append(SPACE);
        builder.append(QUOTE);
        builder.append(PERCENT);
        builder.append(value);
        builder.append(PERCENT);
        builder.append(QUOTE);
        builder.append(SPACE);
        builder.append(AND);
        builder.append(SPACE);
    }
}
