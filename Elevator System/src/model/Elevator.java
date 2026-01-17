package model;

import enums.Direction;
import enums.ElevatorState;
import model.exchange.ElevatorRequest;
import observer.IElevatorObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private final List<IElevatorObserver> observers;
    private final Queue<ElevatorRequest> requests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.observers = new ArrayList<>();
        this.requests = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public Queue<ElevatorRequest> getRequests() {
        return new LinkedList<>(requests);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void addObserver(IElevatorObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IElevatorObserver observer) {
        observers.remove(observer);
    }

    private void notifyStateChange(ElevatorState state) {
        for (IElevatorObserver observer : observers) {
            observer.onElevatorStateChange(this, state);
        }
    }

    private void notifyFloorChange(int floor) {
        for (IElevatorObserver observer : observers) {
            observer.onElevatorFloorChange(this, floor);
        }
    }

    public void setState(ElevatorState state) {
        this.state = state;
        notifyStateChange(state);
    }

    public void setFloor(int floor) {
        this.currentFloor = floor;
        notifyFloorChange(floor);
    }

    public void addRequest(ElevatorRequest request) {
        if (!requests.contains(request)) {
            requests.add(request);
        }

        int requestedFloor = request.getFloor();

        if (state == ElevatorState.IDLE && !requests.isEmpty()) {
            if (requestedFloor > currentFloor) {
                direction = Direction.UP;
            } else if (requestedFloor < currentFloor) {
                direction = Direction.DOWN;
            }
            setState(ElevatorState.MOVING);
        }
    }

    public void moveToNextFloor(int nextFloor) {
        if (state != ElevatorState.MOVING) return;

        while (currentFloor != nextFloor) {
            if (direction == Direction.UP) {
                currentFloor++;
            } else {
                currentFloor--;
            }

            notifyFloorChange(currentFloor);
            if (currentFloor == nextFloor) {
                completeArrival();
                return;
            }
        }
    }

    private void completeArrival() {
        setState(ElevatorState.STOPPED);
        requests.removeIf(request -> request.getFloor() == currentFloor);
        if (requests.isEmpty()) {
            direction = Direction.IDLE;
            setState(ElevatorState.IDLE);
        } else {
            setState(ElevatorState.MOVING);
        }
    }

    public List<Integer> getDestinationFloors() {
        return requests.stream()
                .map(ElevatorRequest::getFloor)
                .toList();
    }
}
