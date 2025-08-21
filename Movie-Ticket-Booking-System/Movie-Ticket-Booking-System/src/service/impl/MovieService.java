package service.impl;

import model.Movie;
import service.IMovieService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieService implements IMovieService {
    private final Map<Integer, Movie> movies;
    private final AtomicInteger movieCounter;

    public MovieService() {
        this.movies = new HashMap<>();
        this.movieCounter = new AtomicInteger(0);
    }

    @Override
    public Movie getMovie(int id) throws Exception {
        if (!movies.containsKey(id)) {
            throw new Exception("Movie with ID " + id + " not found.");
        }
        return movies.get(id);
    }

    @Override
    public Movie createMovie(String name, int durationInMinutes) {
        int id = movieCounter.incrementAndGet();
        Movie movie = new Movie(id, name, durationInMinutes);
        movies.put(id, movie);

        return movie;
    }
}
