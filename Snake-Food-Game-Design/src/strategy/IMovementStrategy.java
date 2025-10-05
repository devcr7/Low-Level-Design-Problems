package strategy;

import enums.Direction;
import utility.Pair;

public interface IMovementStrategy {
    Pair getNextPosition(Pair currentHead, Direction direction);
}
