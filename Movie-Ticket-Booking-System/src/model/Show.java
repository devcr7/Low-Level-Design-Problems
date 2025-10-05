package model;

import java.util.Date;

public class Show {
    private final int id;
    private final Movie movie;
    private final Screen screen;
    private final Date startTime;
    private final Integer durationInMinutes;

    public Show(int id, Movie movie, Screen screen, Date startTime, Integer durationInMinutes) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
    }

    public int getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public Screen getScreen() {
        return screen;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Integer getDurationInMinutes() {
        return durationInMinutes;
    }
}
