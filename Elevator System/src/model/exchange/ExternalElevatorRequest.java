package model.exchange;

import enums.Direction;

public class ExternalElevatorRequest extends ElevatorRequest {

    private final Direction requestDirection;

    public ExternalElevatorRequest(int elevatorId, int floor, Direction requestDirection) {
        super(elevatorId, floor);
        this.requestDirection = requestDirection;
    }

    public Direction getRequestDirection() {
        return requestDirection;
    }
}
