package model.exchange;

import controller.ElevatorController;

public abstract class ElevatorRequest {
    private final int elevatorId;
    private final int floor;

    protected ElevatorRequest(int elevatorId, int floor) {
        this.elevatorId = elevatorId;
        this.floor = floor;
    }

    public int getElevatorId() {
        return elevatorId;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isInternalRequest() {
        return this instanceof InternalElevatorRequest;
    }
}
