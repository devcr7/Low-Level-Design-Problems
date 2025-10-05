package model;

import strategy.impl.KnightMovementStrategy;

import static enums.PieceType.KNIGHT;

public class Knight extends Piece{
    public Knight(boolean isWhitePiece) {
        super(isWhitePiece, new KnightMovementStrategy(), KNIGHT);
    }
}
