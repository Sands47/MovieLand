package com.voligov.movieland.controller;

import com.voligov.movieland.controller.annotation.RoleRequired;
import com.voligov.movieland.entity.Movie;
import com.voligov.movieland.entity.User;
import com.voligov.movieland.service.MovieService;
import com.voligov.movieland.util.JsonConverter;
import com.voligov.movieland.util.entity.GetMovieByIdRequestParams;
import com.voligov.movieland.util.entity.GetMoviesRequestParams;
import com.voligov.movieland.util.entity.MovieSearchParams;
import com.voligov.movieland.util.enums.SortingOrder;
import com.voligov.movieland.util.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.voligov.movieland.util.Constant.AUTHORIZED_USER;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getAllMovies(@RequestParam(value = "rating", required = false) String ratingOrder,
                                               @RequestParam(value = "price", required = false) String priceOrder,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "currency", required = false) String currency) {
        GetMoviesRequestParams params = new GetMoviesRequestParams();
        params.setRatingOrder(SortingOrder.getBySortString(ratingOrder));
        params.setPriceOrder(SortingOrder.getBySortString(priceOrder));
        params.setPage(page);
        params.setCurrency(currency);
        List<Movie> movies = movieService.getAll(params);
        String json = jsonConverter.toJson(movies);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> addMovie(@RequestBody String json) {
        Movie movie = jsonConverter.parseMovie(json);
        movieService.add(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> editMovie(@RequestBody String json) {
        Movie movie = jsonConverter.parseMovie(json);
        movieService.edit(movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{movieId}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> getMovieById(@PathVariable int movieId,
                                               @RequestParam(value = "currency", required = false) String currency,
                                               HttpServletRequest request) {
        GetMovieByIdRequestParams params = new GetMovieByIdRequestParams();
        params.setMovieId(movieId);
        params.setCurrency(currency);
        User authorizedUser = (User) request.getAttribute(AUTHORIZED_USER);
        params.setUser(authorizedUser);
        Movie movie = movieService.getById(params);
        if (movie == null) {
            return new ResponseEntity<>(jsonConverter.wrapError("Movie not found in database"), HttpStatus.BAD_REQUEST);
        } else {
            String json = jsonConverter.toJson(movie);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> searchMovies(@RequestBody String json) {
        MovieSearchParams searchParams = jsonConverter.parseSearchParams(json);
        List<Movie> movies = movieService.search(searchParams);
        String jsonResponse = jsonConverter.toJson(movies);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(value = "/{movieId}", method = RequestMethod.DELETE, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> markMovieForDeletion(@PathVariable int movieId) {
        movieService.markForDeletion(movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RoleRequired(role = UserRole.ADMIN)
    @RequestMapping(value = "/{movieId}/unmark", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ResponseEntity<String> unmarkMovieForDeletion(@PathVariable int movieId) {
        movieService.unmarkForDeletion(movieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/poster/{movieId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getPoster(@PathVariable int movieId) {
        byte[] poster = movieService.getPoster(movieId);
        if (poster == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(poster, HttpStatus.OK);
    }
}
