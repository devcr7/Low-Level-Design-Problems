package strategy;

import model.Elevator;

public interface ISchedulingStrategy {
    int nextStop(Elevator elevator);
}
