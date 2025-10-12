package model;

public class Ticket {
    private final double fare;
    private final Address address;

    public Ticket(double fare, Address address) {
        this.fare = fare;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
