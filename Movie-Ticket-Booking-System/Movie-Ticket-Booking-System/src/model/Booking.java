package model;

import enums.BookingStatus;

import java.util.List;

public class Booking {
    private final int id;
    private final User user;
    private final Show show;
    private final List<Seat> seatsBooked;
    private BookingStatus bookingStatus;
    private final int totalAmount;

    public Booking(int id, User user, Show show, List<Seat> seatsBooked, int totalAmount) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seatsBooked = seatsBooked;
        this.totalAmount = totalAmount;
        this.bookingStatus = BookingStatus.CREATED;
    }

    public boolean isConfirmed() {
        return bookingStatus == BookingStatus.CONFIRMED;
    }

    public void markConfirm() throws Exception {
        if (bookingStatus != BookingStatus.CREATED) {
            throw new Exception("Booking can only be confirmed if it is in CREATED status.");
        }
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void expireBooking() throws Exception {
        if (bookingStatus != BookingStatus.CREATED) {
            throw new Exception("Booking can only be expired if it is in CREATED status.");
        }
        this.bookingStatus = BookingStatus.EXPIRED;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeatsBooked() {
        return seatsBooked;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}
