package strategy;

import model.Cell;
import utilty.Board;

public interface PlayerStrategy {
    Cell makeMove(Board board);
}
