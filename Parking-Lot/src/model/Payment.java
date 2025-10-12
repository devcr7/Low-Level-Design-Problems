package model;

import strategy.IPaymentStrategy;

public class Payment {
    private final double amount;
    private final IPaymentStrategy paymentStrategy;

    public Payment(double amount, IPaymentStrategy paymentStrategy) {
        this.amount = amount;
        this.paymentStrategy = paymentStrategy;
    }

    public void processPayment() {
        if (amount > 0) {
            paymentStrategy.processPayment(amount);
        } else {
            System.out.println("Invalid payment amount.");
        }
    }
}
