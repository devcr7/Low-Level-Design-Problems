package model;

public class Movie {
    private final int id;
    private final String name;
    private final int durationInMinutes;

    public Movie(int id, String name, int durationInMinutes) {
        this.id = id;
        this.name = name;
        this.durationInMinutes = durationInMinutes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
