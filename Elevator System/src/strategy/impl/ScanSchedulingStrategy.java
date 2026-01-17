package strategy.impl;

import enums.Direction;
import model.Elevator;
import model.exchange.ElevatorRequest;
import strategy.ISchedulingStrategy;

import java.util.*;

import static enums.Direction.*;

/**
 * If the elevator is moving up, it will find the [FIFO] upward request and vice versa (this can lead to unnecessary starvation),
 * if it is idle, it figures out the nearest floor either above and below and sets it as the next stop and moves in that direction.
 */
public class ScanSchedulingStrategy implements ISchedulingStrategy {

    private ScanSchedulingStrategy() {}

    private static final class InstanceHolder {
        private static final ScanSchedulingStrategy INSTANCE = new ScanSchedulingStrategy();
    }

    public  static ScanSchedulingStrategy getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public int nextStop(Elevator elevator) {
        Direction elevatorDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        Queue<ElevatorRequest> requestQueue = elevator.getRequests();

        if (requestQueue.isEmpty())
            return currentFloor;

        PriorityQueue<ElevatorRequest> upQueue = new PriorityQueue<>(Comparator.comparingInt(ElevatorRequest::getFloor));
        PriorityQueue<ElevatorRequest> downQueue = new PriorityQueue<>(Comparator.comparingInt(ElevatorRequest::getFloor).reversed());

        while (!requestQueue.isEmpty()) {
            ElevatorRequest elevatorRequest = requestQueue.poll();
            int floor = elevatorRequest.getFloor();
            if (floor > currentFloor) {
                upQueue.add(elevatorRequest);
            } else {
                downQueue.add(elevatorRequest);
            }
        }


        if (elevatorDirection == IDLE) {
            int nearestUpwardFloor = upQueue.isEmpty() ? -1 : upQueue.peek().getFloor();
            int nearestDownwardFloor = downQueue.isEmpty() ? -1 : downQueue.peek().getFloor();

            if (nearestUpwardFloor == -1) {
                elevator.setDirection(DOWN);
                return Objects.requireNonNull(downQueue.poll()).getFloor();
            } else if (nearestDownwardFloor == -1) {
                elevator.setDirection(UP);
                return  Objects.requireNonNull(upQueue.poll()).getFloor();
            } else {
                if (Math.abs(nearestUpwardFloor - currentFloor) < Math.abs(nearestDownwardFloor - currentFloor)) {
                    elevator.setDirection(UP);
                    return Objects.requireNonNull(upQueue.poll()).getFloor();
                } else {
                    elevator.setDirection(DOWN);
                    return Objects.requireNonNull(downQueue.poll()).getFloor();
                }
            }
        } else if (elevatorDirection == UP) {
            return !upQueue.isEmpty() ? upQueue.poll().getFloor() : switchDirection(elevator, downQueue);
        } else {
            return !downQueue.isEmpty() ? downQueue.poll().getFloor() : switchDirection(elevator, upQueue);
        }

    }

    private int switchDirection(Elevator elevator, PriorityQueue<ElevatorRequest> requestsQueue) {
        elevator.setDirection(elevator.getDirection() == UP ? DOWN : UP);
        return requestsQueue.isEmpty() ? elevator.getCurrentFloor() : requestsQueue.poll().getFloor();
    }
}
