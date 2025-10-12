package model;

import enums.VehicleType;
import strategy.IParkingFeeStrategy;

public class Car extends Vehicle {
    public Car(String licencePlate, VehicleType vehicleType, IParkingFeeStrategy parkingFeeStrategy) {
        super(licencePlate, vehicleType, parkingFeeStrategy);
    }
}
