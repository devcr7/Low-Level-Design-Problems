package model;

import enums.VehicleType;
import strategy.IParkingFeeStrategy;

public class Bike extends Vehicle {
    public Bike(String licencePlate, VehicleType vehicleType, IParkingFeeStrategy parkingFeeStrategy) {
        super(licencePlate, vehicleType, parkingFeeStrategy);
    }
}
