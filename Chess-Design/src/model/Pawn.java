package model;

import strategy.impl.PawnMovementStrategy;

import static enums.PieceType.PAWN;

public class Pawn extends Piece{
    public Pawn(boolean isWhitePiece) {
        super(isWhitePiece, new PawnMovementStrategy(), PAWN);
    }
}
