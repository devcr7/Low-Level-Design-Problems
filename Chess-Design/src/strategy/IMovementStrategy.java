package strategy;

import UtilityClasses.Board;
import UtilityClasses.Cell;

public interface IMovementStrategy {
    boolean canMove(Board board, Cell startCell, Cell endCell);
}
