package service;

import model.Movie;
import model.Screen;
import model.Show;

import java.util.Date;

public interface IShowService {
    Show getShow(int id) throws Exception;
    Show createShow(Movie movie, Screen screen, Date startTime, Integer durationInMinutes);
}
