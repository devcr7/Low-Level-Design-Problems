package service.impl;

import enums.SeatCategory;
import model.Screen;
import model.Seat;
import model.Theatre;
import service.ITheatreService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TheatreService implements ITheatreService {
    private final Map<Integer, Theatre> theatres;
    private final Map<Integer, Screen> screens;
    private final Map<Integer, Seat> seats;

    private final AtomicInteger theatreCounter;
    private final AtomicInteger screenCounter;
    private final AtomicInteger seatCounter;

    public TheatreService() {
        theatres = new HashMap<>();
        screens = new HashMap<>();
        seats = new HashMap<>();
        this.theatreCounter = new AtomicInteger(0);
        this.screenCounter = new AtomicInteger(0);
        this.seatCounter = new AtomicInteger(0);
    }

    @Override
    public Seat getSeat(int id) throws Exception {
        if (!seats.containsKey(id)) {
            throw new Exception("Seat with ID " + id + " not found.");
        }

        return seats.get(id);
    }

    @Override
    public Theatre getTheatre(int id) throws Exception {
        if (!theatres.containsKey(id)) {
            throw new Exception("Theatre with ID " + id + " not found.");
        }

        return theatres.get(id);
    }

    @Override
    public Theatre createTheatre(String name) {
        int id = theatreCounter.incrementAndGet();
        Theatre theatre = new Theatre(id, name);
        theatres.put(id, theatre);

        return theatre;
    }

    @Override
    public Screen createScreenInTheatre(String name, Theatre theatre) {
        int id = screenCounter.incrementAndGet();
        Screen screen = new Screen(id, name, theatre);
        screens.put(id, screen);
        theatre.getScreens().add(screen);

        return screen;
    }

    @Override
    public Seat createSeatInScreen(Integer rowNo, SeatCategory category, Screen screen) {
        int id = seatCounter.incrementAndGet();
        Seat seat = new Seat(id, rowNo, category);
        seats.put(id, seat);
        screen.getSeats().add(seat);

        return seat;
    }
}
