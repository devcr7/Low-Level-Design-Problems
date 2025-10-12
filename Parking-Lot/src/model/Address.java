package model;

public class Address {
    private final Integer floorNumber;
    private final Integer spotNumber;

    public Address(Integer floorNumber, Integer spotNumber) {
        this.floorNumber = floorNumber;
        this.spotNumber = spotNumber;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public Integer getSpotNumber() {
        return spotNumber;
    }
}
