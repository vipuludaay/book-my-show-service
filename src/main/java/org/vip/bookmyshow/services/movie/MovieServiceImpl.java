package org.vip.bookmyshow.services.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vip.bookmyshow.models.Movie;
import org.vip.bookmyshow.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);

    private MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Long createMovie(Movie movie) {
        try {
            movieRepository.save(movie);
            return movie.getId();
        } catch (Exception e) {
            LOGGER.error("Error in MovieServiceImpl :: createMovie :: " + e.getMessage());
            throw e;
        }
    }
}
