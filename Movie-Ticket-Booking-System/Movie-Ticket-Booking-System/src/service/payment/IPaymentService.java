package service.payment;

import model.User;

public interface IPaymentService {
    void processPaymentFailed(int bookingId, User user) throws Exception;
    void processPayment(int bookingId, User user, int amount) throws Exception;
}
