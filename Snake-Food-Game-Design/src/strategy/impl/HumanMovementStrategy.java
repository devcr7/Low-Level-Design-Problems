package strategy.impl;

import enums.Direction;
import strategy.IMovementStrategy;
import utility.Pair;

public class HumanMovementStrategy implements IMovementStrategy {
    @Override
    public Pair getNextPosition(Pair currentHead, Direction direction) {
        int row = currentHead.getRow();
        int col = currentHead.getCol();

        return switch (direction) {
            case UP -> new Pair(row - 1, col);
            case DOWN -> new Pair(row + 1, col);
            case LEFT -> new Pair(row, col - 1);
            case RIGHT -> new Pair(row, col + 1);
        };
    }
}
