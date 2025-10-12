package model;

import enums.DurationType;
import enums.VehicleType;
import strategy.IParkingFeeStrategy;

public abstract class Vehicle {
    private final String licencePlate;
    private final VehicleType vehicleType;
    private final IParkingFeeStrategy parkingFeeStrategy;

    protected Vehicle(String licencePlate, VehicleType vehicleType, IParkingFeeStrategy parkingFeeStrategy) {
        this.licencePlate = licencePlate;
        this.vehicleType = vehicleType;
        this.parkingFeeStrategy = parkingFeeStrategy;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public double getParkingFee(int duration, DurationType durationType) {
        return parkingFeeStrategy.calculateFee(vehicleType, duration, durationType);
    }
}
