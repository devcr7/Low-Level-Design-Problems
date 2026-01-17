package observer;

import enums.ElevatorState;
import model.Elevator;

public interface IElevatorObserver extends IObserver {
    void onElevatorStateChange(Elevator elevator, ElevatorState state);
    void onElevatorFloorChange(Elevator elevator, int floor);
}
