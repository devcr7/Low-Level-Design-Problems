package utility;

import enums.SpotType;
import model.Vehicle;

public abstract class ParkingSpot {
    private final int spotNumber;
    private final SpotType spotType;
    private boolean isOccupied;
    private Vehicle vehicle;

    protected ParkingSpot(int spotNumber, SpotType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        isOccupied = false;
    }

    public abstract boolean canParkVehicle(Vehicle vehicle);

    public void parkVehicle(Vehicle vehicle) {
        if (isOccupied) {
            throw new IllegalStateException("Spot is already occupied.");
        }
        if (!canParkVehicle(vehicle)) {
            throw new IllegalArgumentException("This spot is not for " + vehicle.getVehicleType().getValue());
        }

        this.vehicle = vehicle;
        isOccupied = true;
    }

    public void vacate() {
        if (!isOccupied) {
            throw new IllegalStateException("Spot is already vacant.");
        }

        this.vehicle = null;
        isOccupied = false;
    }

    public Integer getSpotNumber() {
        return spotNumber;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
