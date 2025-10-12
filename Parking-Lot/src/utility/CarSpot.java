package utility;

import enums.SpotType;
import model.Vehicle;

import static enums.VehicleType.CAR;

public class CarSpot extends ParkingSpot {
    public CarSpot(int spotNumber, SpotType spotType) {
        super(spotNumber, spotType);
    }

    @Override
    public boolean canParkVehicle(Vehicle vehicle) {
        return vehicle.getVehicleType() == CAR;
    }
}
