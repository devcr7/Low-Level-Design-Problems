package service.impl;

import model.Seat;
import model.Show;
import service.IBookingService;
import service.ISeatAvailabilityService;
import service.ISeatLockProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeatAvailabilityService implements ISeatAvailabilityService {
    private final IBookingService bookingService;
    private final ISeatLockProvider seatLockProvider;

    public SeatAvailabilityService(BookingService bookingService, ISeatLockProvider seatLockProvider) {
        this.bookingService = bookingService;
        this.seatLockProvider = seatLockProvider;
    }

    @Override
    public List<Seat> getAvailableSeats(Show show) {
        List<Seat> allSeats = show.getScreen().getSeats();
        List<Seat> unavailableSeats = getUnavailableSeats(show);
        List<Seat> availableSeats = new ArrayList<>(allSeats);

        availableSeats.removeAll(unavailableSeats);
        return availableSeats;
    }

    private List<Seat> getUnavailableSeats(Show show) {
        List<Seat> unavailableSeats = bookingService.getBookedSeats(show)
                .stream()
                .collect(Collectors.toList());
        unavailableSeats.addAll(seatLockProvider.getLockedSeats(show));
        return unavailableSeats;
    }
}
