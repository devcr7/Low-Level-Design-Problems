package strategy;

import model.Cell;
import utilty.Board;

public interface IPlayerStrategy {
    Cell makeMove(Board board);
}
