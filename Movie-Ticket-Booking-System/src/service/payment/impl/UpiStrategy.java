package service.payment.impl;

import service.payment.IPaymentStrategy;

public class UpiStrategy implements IPaymentStrategy {
    @Override
    public Boolean processPayment(int amount) {
        System.out.println("Payment of " + amount + " completed via upi.");
        return true;
    }
}
