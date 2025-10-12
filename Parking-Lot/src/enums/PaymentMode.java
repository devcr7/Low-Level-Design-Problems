package enums;

public enum PaymentMode {
    CASH("Cash"),
    CREDIT_CARD("Credit Card");

    private final String value;

    PaymentMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentMode fromString(String str) {
        for (PaymentMode mode: PaymentMode.values()) {
            if (mode.value.equalsIgnoreCase(str)) {
                return mode;
            }
        }
        return CREDIT_CARD;
    }
}
