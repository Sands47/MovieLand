package com.voligov.movieland.util;

import com.voligov.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JsonConverter {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String COMMA_SEPARATOR = ",";
    private static final String COLON_SEPARATOR = ":";
    private static final String CURLY_BRACKET_OPEN = "{";
    private static final String CURLY_BRACKET_CLOSE = "}";

    public String toJson(List<Movie> movies) {
        log.info("Start converting a list of movies to JSON array");
        StringBuilder stringBuilder = new StringBuilder(addQuotes("movies"));
        stringBuilder.append(":[\n");
        String[] fieldNames = {"name", "name_original", "release_year", "description", "rating", "price", "genres", "countries"};
        for (Movie movie : movies) {
            Object[] fields = {movie.getName(), movie.getNameOriginal(), movie.getReleaseYear(), movie.getDescription(),
                    movie.getRating(), movie.getPrice(), movie.getGenres(), movie.getCountries()};
            stringBuilder.append(CURLY_BRACKET_OPEN);
            for (int i = 0; i < fieldNames.length; i++) {
                stringBuilder.append(addQuotes(fieldNames[i]));
                stringBuilder.append(COLON_SEPARATOR);
                stringBuilder.append(addQuotes(fields[i]));
                if (i != fieldNames.length - 1) {
                    stringBuilder.append(COMMA_SEPARATOR);
                }
            }
            stringBuilder.append(CURLY_BRACKET_CLOSE);
            if (!movie.equals(movies.get(movies.size() - 1))) {
                stringBuilder.append(COMMA_SEPARATOR);
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("]");
        log.info("Finished converting a list of movies to JSON: {}", stringBuilder);
        return stringBuilder.toString();
    }

    private String addQuotes(Object value) {
        return "\"" + value + "\"";
    }
}
