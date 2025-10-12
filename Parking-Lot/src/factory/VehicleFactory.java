package factory;

import enums.VehicleType;
import model.Bike;
import model.Car;
import model.OtherVehicle;
import model.Vehicle;
import strategy.IParkingFeeStrategy;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType vehicleType, String licensePlate, IParkingFeeStrategy parkingFeeStrategy) {
        return switch (vehicleType) {
            case BIKE -> new Bike(licensePlate, vehicleType, parkingFeeStrategy);
            case CAR -> new Car(licensePlate, vehicleType, parkingFeeStrategy);
            default -> new OtherVehicle(licensePlate, vehicleType, parkingFeeStrategy);
        };
    }
}
