package service;

import model.Movie;

public interface IMovieService {
    Movie getMovie(int id) throws Exception;
    Movie createMovie(String name, int durationInMinutes);
}
