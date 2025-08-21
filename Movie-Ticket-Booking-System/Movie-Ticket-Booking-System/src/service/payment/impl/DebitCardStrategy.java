package service.payment.impl;

import service.payment.IPaymentStrategy;

public class DebitCardStrategy implements IPaymentStrategy {
    @Override
    public Boolean processPayment(int amount) {
        System.out.println("Payment of " + amount + " completed via debit card.");
        return true;
    }
}
