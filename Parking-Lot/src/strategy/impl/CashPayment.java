package strategy.impl;

import strategy.IPaymentStrategy;

public class CashPayment implements IPaymentStrategy {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of INR" + amount);
    }
}
