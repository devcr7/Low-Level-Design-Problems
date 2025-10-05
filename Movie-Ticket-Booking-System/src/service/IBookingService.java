package service;

import model.Booking;
import model.Seat;
import model.Show;
import model.User;

import java.util.List;
import java.util.Set;

public interface IBookingService {
    Booking getBooking(int id) throws Exception;
    List<Booking> getAllBookings(Show show);
    Booking createBooking(User user, Show show, List<Seat> seats, int amount) throws Exception;
    Set<Seat> getBookedSeats(Show show);
    void markConfirm(Booking booking, User user) throws Exception;
    Boolean isAnySeatAlreadyBooked(Show show, List<Seat> seats);
}
