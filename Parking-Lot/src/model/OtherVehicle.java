package model;

import enums.VehicleType;
import strategy.IParkingFeeStrategy;

public class OtherVehicle extends Vehicle {
    public OtherVehicle(String licencePlate, VehicleType vehicleType, IParkingFeeStrategy parkingFeeStrategy) {
        super(licencePlate, vehicleType, parkingFeeStrategy);
    }
}
