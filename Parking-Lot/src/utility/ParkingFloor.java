package utility;

import model.Address;
import model.Vehicle;

import java.util.List;

public class ParkingFloor {
    private final Integer floorNumber;
    private final List<ParkingSpot> parkingSpots;

    public ParkingFloor(int floorNumber, List<ParkingSpot> parkingSpots) {
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
    }

    public Address parkVehicle(Vehicle vehicle) {
        for (ParkingSpot parkingSpot: parkingSpots) {
            if (!parkingSpot.isOccupied() && parkingSpot.canParkVehicle(vehicle)) {
                parkingSpot.parkVehicle(vehicle);
                System.out.println("Parked vehicle with licensePlatNo. " + vehicle.getLicencePlate() + " at floor number: " + floorNumber +
                        " and parking spot number: " + parkingSpot.getSpotNumber());
                return new Address(floorNumber, parkingSpot.getSpotNumber());
            }
        }
        return null;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }
}
