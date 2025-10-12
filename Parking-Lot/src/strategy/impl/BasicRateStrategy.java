package strategy.impl;

import enums.DurationType;
import enums.VehicleType;
import strategy.IParkingFeeStrategy;

import static enums.DurationType.HOURS;

public class BasicRateStrategy implements IParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        return switch (vehicleType) {
            case BIKE -> durationType == HOURS
                    ? duration * 3
                    : duration * 3 * 24;
            case CAR -> durationType == HOURS
                    ? duration * 8
                    : duration * 8 * 24;
            case TRUCK -> durationType == HOURS
                    ? duration * 12
                    : duration * 12 * 24;
        };
    }
}
