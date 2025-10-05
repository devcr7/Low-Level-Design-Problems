package model;

import strategy.impl.KingMovementStrategy;

import static enums.PieceType.KING;

public class King extends Piece{
    public King(boolean isWhitePiece) {
        super(isWhitePiece, new KingMovementStrategy(), KING);
    }
}
