package model;

import strategy.impl.QueenMovementStrategy;

import static enums.PieceType.QUEEN;

public class Queen extends Piece{
    public Queen(boolean isWhitePiece) {
        super(isWhitePiece, new QueenMovementStrategy(), QUEEN);
    }
}
