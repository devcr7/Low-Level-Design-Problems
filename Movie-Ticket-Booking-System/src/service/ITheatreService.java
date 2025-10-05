package service;

import enums.SeatCategory;
import model.Screen;
import model.Seat;
import model.Theatre;

public interface ITheatreService {
    Seat getSeat(int id) throws Exception;
    Theatre getTheatre(int id) throws Exception;
    Theatre createTheatre(String name);
    Screen createScreenInTheatre(String name, Theatre theatre);
    Seat createSeatInScreen(Integer rowNo, SeatCategory category, Screen screen);
}
