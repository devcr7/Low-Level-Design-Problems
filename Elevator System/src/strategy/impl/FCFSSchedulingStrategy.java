package strategy.impl;

import enums.Direction;
import model.exchange.ElevatorRequest;
import strategy.ISchedulingStrategy;
import model.Elevator;

import java.util.Queue;

import static enums.Direction.*;

/**
 * This essentially follows FIFO for all the requests no matter what
 */
public class FCFSSchedulingStrategy implements ISchedulingStrategy {

    private FCFSSchedulingStrategy() {}

    private static final class InstanceHolder {
        private static final FCFSSchedulingStrategy instance = new FCFSSchedulingStrategy();
    }

    public static FCFSSchedulingStrategy getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public int nextStop(Elevator elevator) {
        Direction elevatorDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        Queue<ElevatorRequest> requestQueue = elevator.getRequests();

        if (requestQueue.isEmpty())
            return currentFloor;

        int nextRequestedFloor = requestQueue.poll().getFloor();
        if (nextRequestedFloor == currentFloor)
            return currentFloor;

        if (elevatorDirection == IDLE) {
            elevator.setDirection(nextRequestedFloor > currentFloor ? UP : DOWN);
        } else if (elevatorDirection == UP && nextRequestedFloor < currentFloor) {
            elevator.setDirection(DOWN);
        } else if (nextRequestedFloor > currentFloor) {
            elevator.setDirection(UP);
        }

        return nextRequestedFloor;
    }
}
