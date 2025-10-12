package strategy.impl;

import enums.DurationType;
import enums.VehicleType;
import strategy.IParkingFeeStrategy;

import static enums.DurationType.HOURS;

public class PremiumRateStrategy implements IParkingFeeStrategy {
    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        return switch (vehicleType) {
            case BIKE -> durationType == HOURS
                    ? duration * 5
                    : duration * 5 * 24;
            case CAR -> durationType == HOURS
                    ? duration * 10
                    : duration * 10 * 24;
            case TRUCK -> durationType == HOURS
                    ? duration * 15
                    : duration * 15 * 24;
        };
    }
}
