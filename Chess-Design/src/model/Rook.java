package model;

import strategy.impl.RookMovementStrategy;

import static enums.PieceType.ROOK;

public class Rook extends Piece{
    public Rook(boolean isWhitePiece) {
        super(isWhitePiece, new RookMovementStrategy(), ROOK);
    }
}
