package strategy.impl;

import strategy.IPaymentStrategy;

public class CreditCardPayment implements IPaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment of INR" + amount);
    }
}
