package strategy;

import enums.DurationType;
import enums.VehicleType;

public interface IParkingFeeStrategy {
    double calculateFee(VehicleType vehicleType, int duration, DurationType durationType);
}
