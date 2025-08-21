package service.impl;

import model.Movie;
import model.Screen;
import model.Show;
import service.IShowService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowService implements IShowService {
    private final Map<Integer, Show> shows;
    private final AtomicInteger showCounter;

    public ShowService() {
        this.shows = new HashMap<>();
        showCounter = new AtomicInteger(0);
    }

    @Override
    public Show getShow(int id) throws Exception{
        if (!shows.containsKey(id)) {
            throw new Exception("Show with ID " + id + " not found");
        }

        return shows.get(id);
    }

    @Override
    public Show createShow(Movie movie, Screen screen, Date startTime, Integer durationInMinutes) {
        int id = showCounter.incrementAndGet();
        Show show = new Show(id, movie, screen, startTime, durationInMinutes);
        shows.put(id, show);

        return show;
    }
}
