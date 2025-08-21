import enums.SeatCategory;
import model.*;
import service.ISeatLockProvider;
import service.impl.*;
import service.payment.impl.PaymentService;
import service.payment.impl.UpiStrategy;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        MovieService movieService = new MovieService();
        TheatreService theatreService = new TheatreService();
        ShowService showService = new ShowService();

        ISeatLockProvider seatLockProvider = new SeatLockProvider(300);

        BookingService bookingService = new BookingService(seatLockProvider);

        SeatAvailabilityService seatAvailabilityService = new SeatAvailabilityService(bookingService, seatLockProvider);

        PaymentService paymentService = new PaymentService(new UpiStrategy(), bookingService);

        System.out.println("Creating a new theatre...");
        Theatre theatre = theatreService.createTheatre("PVR Cinemas");
        System.out.println("Theatre created with ID " + theatre.getId());

        System.out.println("Creating new screen...");
        Screen screen = theatreService.createScreenInTheatre("Screen 1", theatre);
        System.out.println("Screen created with ID " + screen.getId());

        System.out.println("Creating seats...");
        for (int row = 1; row <= 5; row++) {
            SeatCategory seatCategory;
            if (row == 1) {
                seatCategory = SeatCategory.PLATINUM;
            } else if (row <= 3) {
                seatCategory = SeatCategory.GOLD;
            } else {
                seatCategory = SeatCategory.SILVER;
            }

            for (int seatNum = 1; seatNum <= 10; seatNum++) {
                Seat seat = theatreService.createSeatInScreen(row, seatCategory, screen);
                System.out.println("Created seat at row " + row + " with ID: " + seat.getId() + " and category " + seatCategory);
            }
        }

        System.out.println("Creating a new movie...");
        Movie movie = movieService.createMovie("Interstellar", 123);
        System.out.println("Movie created with ID " + movie.getId());

        System.out.println("Creating a new show...");
        Show show = showService.createShow(movie, screen, new Date(), movie.getDurationInMinutes());
        System.out.println("Show created with ID " + show.getId());

        System.out.println("Checking available seats...");
        List<Seat> availableSeats = seatAvailabilityService.getAvailableSeats(show);
        System.out.println("Available seats: " + availableSeats);

        System.out.println("Creating user...");
        User user = new User("John Doe", "fasfasfd@example.com");
        System.out.println("User created with email " + user.getEmail());

        System.out.println("Sequential booking of seats 1, 2, 3...");
        Booking booking = bookingService.createBooking(user, show,
                Arrays.asList(theatreService.getSeat(1), theatreService.getSeat(2), theatreService.getSeat(3)), 900);
        System.out.println("Booking created with ID " + booking.getId());

        System.out.println("Processing payment...");
        paymentService.processPayment(booking.getId(), user, booking.getTotalAmount());
        System.out.println("Payment processed successfully!");

        Booking verifyBooking = bookingService.getBooking(booking.getId());
        System.out.println("Booking status: " + verifyBooking.getBookingStatus());
        System.out.println("Is booking confirmed? " + verifyBooking.isConfirmed());

        System.out.println("Checking available seats after booking...");
        availableSeats = seatAvailabilityService.getAvailableSeats(show);
        System.out.println("Available seats: " + availableSeats.stream().map(seat -> seat.getId()).collect(Collectors.toList()));

        System.out.println("Simulating concurrent booking attempts...");
        Thread t1 = new Thread(() -> {
            try {
                Booking concurrentBooking = bookingService.createBooking(user, show,
                        Arrays.asList(theatreService.getSeat(4), theatreService.getSeat(5), theatreService.getSeat(6)), 900);
                System.out.println("User1 booked seats (5, 6, 7) with booking ID " + concurrentBooking.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                User user2 = new User("Doe John", "325346hdfh@example.com");
                Booking concurrentBooking = bookingService.createBooking(user2, show,
                        Arrays.asList(theatreService.getSeat(7), theatreService.getSeat(8), theatreService.getSeat(9)), 900);
                System.out.println("User2 booked seats (7, 8, 9) with booking ID " + concurrentBooking.getId());
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final available seats after concurrent booking attempts: " + seatAvailabilityService.getAvailableSeats(show).stream().map(seat -> seat.getId()).collect(Collectors.toList()));
    }
}