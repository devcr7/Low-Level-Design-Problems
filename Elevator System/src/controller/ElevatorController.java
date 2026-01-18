package controller;

import enums.Direction;
import enums.SchedulingStrategy;
import factory.SchedulingStrategyFactory;
import model.Elevator;
import model.Floor;
import model.exchange.ExternalElevatorRequest;
import model.exchange.InternalElevatorRequest;
import strategy.ISchedulingStrategy;
import strategy.impl.ScanSchedulingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private List<Elevator> elevators;
    private List<Floor> floors;
    private ISchedulingStrategy schedulingStrategy;
    private int currentElevatorId;

    public ElevatorController(int numberOfElevators, int numberOfFloors) {
        this.elevators = new ArrayList<Elevator>();
        this.floors = new ArrayList<>();
        this.schedulingStrategy = ScanSchedulingStrategy.getInstance();
        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new Elevator(i + 1));
        }

        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i + 1));
        }
    }

    public void setSchedulingStrategy(SchedulingStrategy strategy) {
        this.schedulingStrategy = SchedulingStrategyFactory.getSchedulingStrategy(strategy);
    }

    public void requestElevator(int elevatorId, int floorNumber, Direction direction) {
        System.out.println("External request: Floor " + floorNumber + ", Direction " + direction);
        Elevator selectedElevator = getElevatorById(elevatorId);

        if (selectedElevator != null) {
            selectedElevator.addRequest(new ExternalElevatorRequest(elevatorId, floorNumber, direction));
            System.out.println("Assigned elevator " + selectedElevator.getId() + " to floor " + floorNumber);
        } else {
            System.out.println("No elevator with id " + elevatorId + " found");
        }
    }

    public void requestFloor(int currentElevatorId, int floorNumber) {
        Elevator elevator = getElevatorById(currentElevatorId);
        assert elevator != null;
        System.out.println("Internal request: Elevator " + elevator.getId());

        elevator.addRequest(new InternalElevatorRequest(currentElevatorId, floorNumber));
    }

    public void step() {
        for (Elevator elevator : elevators) {
            if (!elevator.getRequests().isEmpty()) {
                int nextStop = schedulingStrategy.nextStop(elevator);
                elevator.moveToNextFloor(nextStop);
            }
        }
    }

    private Elevator getElevatorById(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId) {
                return elevator;
            }
        }
        return null;
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setCurrentElevatorId(int currentElevatorId) {
        this.currentElevatorId = currentElevatorId;
    }
}
