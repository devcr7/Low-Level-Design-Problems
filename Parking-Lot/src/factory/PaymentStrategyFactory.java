package factory;

import enums.PaymentMode;
import strategy.IPaymentStrategy;
import strategy.impl.CashPayment;
import strategy.impl.CreditCardPayment;

public class PaymentStrategyFactory {
    public static IPaymentStrategy getPaymentStrategy(PaymentMode mode) {
        return switch (mode) {
            case CASH -> new CashPayment();
            case CREDIT_CARD -> new CreditCardPayment();
        };
    }
}
