package service.payment;

public interface IPaymentStrategy {
    Boolean processPayment(int amount);
}
