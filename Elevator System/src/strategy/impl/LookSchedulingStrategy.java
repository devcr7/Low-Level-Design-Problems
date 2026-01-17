package strategy.impl;

import enums.Direction;
import model.Elevator;
import model.exchange.ElevatorRequest;
import model.exchange.ExternalElevatorRequest;
import model.exchange.InternalElevatorRequest;
import strategy.ISchedulingStrategy;

import java.util.Queue;

import static enums.Direction.DOWN;
import static enums.Direction.UP;

/**
 * Prevents starvation as observed in scan scheduling algorithm, gives priority to FIFO queue request but serves
 * the internal requests along the primary request direction or external request having the same direction if exists between
 * current and primary target floor.
 */
public class LookSchedulingStrategy implements ISchedulingStrategy {

    private LookSchedulingStrategy() {}

    private static final class InstanceHolder {
        private static final LookSchedulingStrategy INSTANCE = new LookSchedulingStrategy();
    }

    public static LookSchedulingStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public int nextStop(Elevator elevator) {
        int currentFloor = elevator.getCurrentFloor();
        Queue<ElevatorRequest> requestQueue = elevator.getRequests();

        if (requestQueue == null || requestQueue.isEmpty()) {
            return currentFloor;
        }

        ElevatorRequest primaryRequest = requestQueue.peek();
        int primaryFloor = primaryRequest.getFloor();

        if (primaryFloor == currentFloor) {
            return currentFloor;
        }

        Direction travelDirection;
        if (primaryFloor > currentFloor) {
            travelDirection = UP;
        } else {
            travelDirection = DOWN;
        }

        Integer targetFloor = null;

        int minAboveFloor = Integer.MAX_VALUE;
        int maxBelowFloor = Integer.MAX_VALUE;

        for (ElevatorRequest request: requestQueue) {

            boolean shouldServeRequestAlongDirection = false;
            ExternalElevatorRequest externalRequest;

            if (request.getClass().equals(InternalElevatorRequest.class)) {
                shouldServeRequestAlongDirection = true;
            } else {
                externalRequest = (ExternalElevatorRequest) request;
                if (externalRequest.getRequestDirection() == travelDirection) {
                    shouldServeRequestAlongDirection = true;
                }
            }

            int requestFloor = request.getFloor();
            if (travelDirection == UP && requestFloor > currentFloor && primaryFloor >= requestFloor) {
                if (shouldServeRequestAlongDirection && (requestFloor - currentFloor < minAboveFloor)) {
                    minAboveFloor = requestFloor - currentFloor;
                    targetFloor = requestFloor;
                }
            } else if (travelDirection == DOWN && requestFloor < currentFloor && primaryFloor <= requestFloor) {
                if (shouldServeRequestAlongDirection && (currentFloor - requestFloor < maxBelowFloor)) {
                    maxBelowFloor = currentFloor - requestFloor;
                    targetFloor = requestFloor;
                }
            }
        }

        return targetFloor == null ? primaryFloor : targetFloor;
    }
}
