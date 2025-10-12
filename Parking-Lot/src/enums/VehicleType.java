package enums;

public enum VehicleType {
    BIKE("Bike"),
    CAR("Car"),
    TRUCK("Truck");

    private final String vehicleType;

    VehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getValue() {
        return vehicleType;
    }

    public static VehicleType fromString(String str) {
        for (VehicleType vehicle: VehicleType.values()) {
            if (vehicle.vehicleType.equals(str)) {
                return vehicle;
            }
        }
        return null;
    }
}
