package observer.impl;

import enums.ElevatorState;
import model.Elevator;
import observer.IElevatorObserver;

public class ElevatorDisplay implements IElevatorObserver {
    @Override
    public void onElevatorStateChange(Elevator elevator, ElevatorState state) {
        System.out.println("Elevator " + elevator.getId() + " state changed to " + elevator.getState());
    }

    @Override
    public void onElevatorFloorChange(Elevator elevator, int floor) {
        System.out.println("Elevator " + elevator.getId() + " moved to floor " + elevator.getCurrentFloor());
    }
}
