package service.impl;

import model.Booking;
import model.Seat;
import model.Show;
import model.User;
import service.IBookingService;
import service.ISeatLockProvider;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookingService implements IBookingService {
    private final Map<Integer, Booking> bookings;
    private final ISeatLockProvider seatLockProvider;
    private final AtomicInteger bookingCounter;

    public BookingService(ISeatLockProvider seatLockProvider) {
        bookings = new ConcurrentHashMap<>();
        this.seatLockProvider = seatLockProvider;
        bookingCounter = new AtomicInteger(0);
    }

    @Override
    public Booking getBooking(int id) throws Exception {
        if (!bookings.containsKey(id)) {
            throw new Exception("Booking with ID " + id + " not found");
        }
        return bookings.get(id);
    }

    @Override
    public List<Booking> getAllBookings(Show show) {
        return bookings.values()
                .stream()
                .filter(booking -> booking.getShow().equals(show))
                .collect(Collectors.toList());
    }

    @Override
    public Booking createBooking(User user, Show show, List<Seat> seats, int amount) throws Exception {
        if (isAnySeatAlreadyBooked(show, seats)) {
            throw new Exception("Seat already booked");
        }

        seatLockProvider.lockSeats(show, seats, user);
        int id = bookingCounter.incrementAndGet();
        Booking booking = new Booking(id, user, show, seats, amount);

        bookings.put(id, booking);
        return booking;
    }

    @Override
    public Set<Seat> getBookedSeats(Show show) {
        return getAllBookings(show)
                .stream()
                .filter(Booking::isConfirmed)
                .map(Booking::getSeatsBooked)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public void markConfirm(Booking booking, User user) throws Exception {
        if (!booking.getUser().equals(user)) {
            throw new Exception("Cannot confirm a booking made by another user");
        }

        for (Seat seat: booking.getSeatsBooked()) {
            if (!seatLockProvider.validateSeatLock(booking.getShow(), seat, user)) {
                throw new Exception("Acquired lock is either invalid or expired");
            }
        }
        booking.markConfirm();
    }

    @Override
    public Boolean isAnySeatAlreadyBooked(Show show, List<Seat> seats) {
        Set<Seat> bookedSeats = getBookedSeats(show);
        for (Seat seat: seats) {
            if (bookedSeats.contains(seat)) {
                return true;
            }
        }
        return false;
    }
}
