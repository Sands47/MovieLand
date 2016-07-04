package com.voligov.movieland.dao.impl;

import com.voligov.movieland.dao.MovieDao;
import com.voligov.movieland.dao.impl.mapper.MoviePosterRowMapper;
import com.voligov.movieland.dao.impl.mapper.MovieRowMapper;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.util.QueryBuilder;
import com.voligov.movieland.util.entity.GetMovieByIdRequestParams;
import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.voligov.movieland.util.Constant.MOVIE_IDS;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private String getAllMoviesSQL;

    @Autowired
    private String getMovieByIdSQL;

    @Autowired
    private String addMovieSQL;

    @Autowired
    private String getMovieIdSQL;

    @Autowired
    private String updateMovieSQL;

    @Autowired
    private String deleteMoviesSQL;

    @Autowired
    private String getPosterSQL;

    private final QueryBuilder queryBuilder = new QueryBuilder();

    private final MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final MoviePosterRowMapper posterRowMapper = new MoviePosterRowMapper();

    @Override
    public List<Movie> getAll(GetMoviesRequestParams params) {
        String query = queryBuilder.buildPagedQuery(params, getAllMoviesSQL);
        return jdbcTemplate.query(query, movieRowMapper);
    }

    @Override
    public Movie getById(GetMovieByIdRequestParams params) {
        Movie movie;
        try {
            movie = jdbcTemplate.queryForObject(getMovieByIdSQL, movieRowMapper, params.getMovieId());
        } catch (EmptyResultDataAccessException e) {
            log.warn("Movie with id = {} not found in database", params.getMovieId());
            return null;
        }
        return movie;
    }

    @Override
    public List<Movie> search(MovieSearchParams searchParams) {
        String query = queryBuilder.buildSearchQuery(searchParams, getAllMoviesSQL);
        return jdbcTemplate.query(query, movieRowMapper);
    }

    @Override
    public void add(Movie movie) {
        jdbcTemplate.update(addMovieSQL, movie.getName(), movie.getNameOriginal(), movie.getReleaseYear(),
                movie.getDescription(), movie.getPrice());
        try {
            Integer movieId = jdbcTemplate.queryForObject(getMovieIdSQL, Integer.class, movie.getName(), movie.getNameOriginal(), movie.getDescription());
            log.info("Movie {} added to database. Id {} generated", movie, movieId);
            movie.setId(movieId);
        } catch (IncorrectResultSizeDataAccessException e) {
            log.info("Movie already exists in the database");
            throw new RuntimeException("Movie already exists in the database");
        }
    }

    @Override
    public void edit(Movie movie) {
        jdbcTemplate.update(updateMovieSQL, movie.getName(), movie.getNameOriginal(), movie.getReleaseYear(),
                movie.getDescription(), movie.getPrice(), movie.getId());
        log.info("Movie {} updated in database", movie.getId());
    }

    @Override
    public void deleteMovies(List<Integer> movies) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue(MOVIE_IDS, movies);
        namedParameterJdbcTemplate.update(deleteMoviesSQL, parameters);
    }

    @Override
    public byte[] getPoster(Integer movieId) {
        //CREATE TABLE movie_poster(movie_id int, poster mediumblob);
        /*String insertPoster = "INSERT INTO movie_poster(movie_id,poster) VALUES (1,?);";
        final File image = new File("C:\\Users\\Sands\\Downloads\\shawshank.jpg");
        final InputStream imageIs;
        try {
            imageIs = new FileInputStream(image);
            LobHandler lobHandler = new DefaultLobHandler();
            jdbcTemplate.update(
                    insertPoster,
                    new Object[]{
                            new SqlLobValue(imageIs, (int) image.length(), lobHandler),
                    },
                    new int[]{Types.BLOB});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;*/
        byte[] fileBytes = null;
        try {
            fileBytes = jdbcTemplate.queryForObject(getPosterSQL, posterRowMapper, movieId);
        } catch (EmptyResultDataAccessException e) {
            log.info("Poster for movie id = {} doesn't exist in database", movieId);
        }
        return fileBytes;
    }
}
