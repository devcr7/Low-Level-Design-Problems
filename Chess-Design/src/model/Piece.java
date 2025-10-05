package model;

import UtilityClasses.Board;
import UtilityClasses.Cell;
import enums.PieceType;
import strategy.IMovementStrategy;

public abstract class Piece {
    private final boolean isWhite;
    private boolean killed;
    protected final IMovementStrategy movementStrategy;
    protected final PieceType pieceType;

    protected Piece(boolean isWhite, IMovementStrategy movementStrategy, PieceType pieceType) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
        this.pieceType = pieceType;
        killed = false;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean canMove(Board board, Cell startCell, Cell endCell) {
        return movementStrategy.canMove(board, startCell,endCell);
    }
}
