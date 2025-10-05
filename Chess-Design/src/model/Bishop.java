package model;

import strategy.impl.BishopMovementStrategy;

import static enums.PieceType.BISHOP;

public class Bishop extends Piece{
    public Bishop(boolean isWhitePiece) {
        super(isWhitePiece, new BishopMovementStrategy(), BISHOP);
    }
}
