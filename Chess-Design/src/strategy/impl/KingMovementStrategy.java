package strategy.impl;

import UtilityClasses.Board;
import UtilityClasses.Cell;
import strategy.IMovementStrategy;

public class KingMovementStrategy implements IMovementStrategy {
    @Override
    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return true;
    }
}
