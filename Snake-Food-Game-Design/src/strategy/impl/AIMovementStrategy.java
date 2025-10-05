package strategy.impl;

import enums.Direction;
import strategy.IMovementStrategy;
import utility.Pair;

public class AIMovementStrategy implements IMovementStrategy {
    @Override
    public Pair getNextPosition(Pair currentHead, Direction direction) {
        // AI can essentially use path finding algorithms
        return currentHead;
    }
}
