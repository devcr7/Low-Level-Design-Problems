package service.payment.impl;

import model.Booking;
import model.User;
import service.IBookingService;
import service.payment.IPaymentService;
import service.payment.IPaymentStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService implements IPaymentService {
    private final Map<Booking, Integer> bookingFailures;

    private final IPaymentStrategy paymentStrategy;
    private final IBookingService bookingService;

    public PaymentService(IPaymentStrategy paymentStrategy, IBookingService bookingService) {
        this.bookingFailures = new ConcurrentHashMap<>();
        this.paymentStrategy = paymentStrategy;
        this.bookingService = bookingService;
    }

    @Override
    public void processPaymentFailed(int bookingId, User user) throws Exception {
        Booking booking = bookingService.getBooking(bookingId);
        if (!booking.getUser().equals(user)) {
            throw new Exception("Only booking owner can report payment failure");
        }

        if (!bookingFailures.containsKey(booking)) {
            bookingFailures.put(booking, 0);
        }

        final Integer currentFailureCount = bookingFailures.get(booking);
        final Integer newFailureCount = currentFailureCount + 1;
        bookingFailures.put(booking, newFailureCount);

        System.out.println("Could not process the payment for booking with ID " + bookingId);
    }

    @Override
    public void processPayment(int bookingId, User user, int amount) throws Exception {
        if (paymentStrategy.processPayment(amount)) {
            bookingService.markConfirm(bookingService.getBooking(bookingId), user);
        } else {
            processPaymentFailed(bookingId, user);
        }
    }
}
