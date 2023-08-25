package org.vip.bookmyshow.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vip.bookmyshow.models.Movie;
import org.vip.bookmyshow.services.movie.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("")
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        ResponseEntity<?> response = null;
        try {
            Long movieId = movieService.createMovie(movie);
            response = ResponseEntity.ok(movieId);
        } catch (Exception e) {
            LOGGER.error("Error in MovieController :: createMovie :: "  + e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please try again later!");
        }
        return response;
    }
}
