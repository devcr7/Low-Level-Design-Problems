package utility;

import model.Address;
import model.Vehicle;

import java.util.ArrayList;
import java.util.List;

import static enums.SpotType.BIKE_SPOT;
import static enums.SpotType.CAR_SPOT;

public class ParkingLot {
    private static ParkingLot instance;
    private final List<ParkingFloor> parkingFloors;

    private ParkingLot(int floorsCount, int spotsPerFloor) {
        parkingFloors = new ArrayList<>();
        initializeParkingLot(floorsCount, spotsPerFloor);
    }

    public static ParkingLot getInstance(int floorsCount, int spotsPerFloor) {
        if (instance == null) {
            instance = new ParkingLot(floorsCount, spotsPerFloor);
        }
        return instance;
    }

    private void initializeParkingLot(int floorsCount, int spotsPerFloor) {
        for (int floor = 1; floor <= floorsCount; floor++) {
            parkingFloors.add(new ParkingFloor(floor, new ArrayList<>()));
        }

        for (ParkingFloor parkingFloor: parkingFloors) {
            List<ParkingSpot> parkingSpots = parkingFloor.getParkingSpots();
            for (int spot = 1; spot <= spotsPerFloor; spot++) {
                ParkingSpot newSpot;
                if (spot % 2 == 0) {
                    newSpot = new BikeSpot(spot, BIKE_SPOT) ;
                } else {
                    newSpot = new CarSpot(spot, CAR_SPOT);
                }
                parkingSpots.add(newSpot);
            }
        }
    }

    public Address parkVehicle(Vehicle vehicle) {
        for (ParkingFloor parkingFloor: parkingFloors) {
            Address address = parkingFloor.parkVehicle(vehicle);
            if (address != null) {
                return address;
            }
        }
        System.out.println("Cannot Park due to unavailability or unsuitability.");
        return null;
    }

    public void vacate(Address address) {
        Integer floorNumber = address.getFloorNumber();
        Integer spotNumber = address.getSpotNumber();

        for (ParkingFloor parkingFloor: parkingFloors) {
            if (parkingFloor.getFloorNumber().equals(floorNumber)) {
                for (ParkingSpot parkingSpot : parkingFloor.getParkingSpots()) {
                    if (parkingSpot.getSpotNumber().equals(spotNumber)) {
                        System.out.println("Vacated the spot for floor: " + parkingFloor.getFloorNumber() + ", spot number: "
                                + parkingSpot.getSpotNumber() + ", and vehicle licensePlateNo. " + parkingSpot.getVehicle().getLicencePlate());
                        parkingSpot.vacate();
                        return;
                    }
                }
            }
        }

        System.out.println("Incorrect address!");
    }
}
