package model;

import enums.SpotType;
import utility.ParkingSpot;

import static enums.VehicleType.BIKE;

public class BikeSpot extends ParkingSpot {
    public BikeSpot(int spotNumber, SpotType spotType) {
        super(spotNumber, spotType);
    }

    @Override
    public boolean canParkVehicle(Vehicle vehicle) {
        return vehicle.getVehicleType() == BIKE;
    }
}
