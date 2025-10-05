package service;

import model.Seat;
import model.Show;

import java.util.List;

public interface ISeatAvailabilityService {
    List<Seat> getAvailableSeats(Show show);
}
