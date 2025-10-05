package factory;

import enums.PieceType;
import model.*;

public class PieceFactory {
    public static Piece createPiece(PieceType pieceType, boolean isWhitePiece) {
        return switch (pieceType) {
            case KING -> new King(isWhitePiece);
            case QUEEN -> new Queen(isWhitePiece);
            case KNIGHT -> new Knight(isWhitePiece);
            case BISHOP -> new Bishop(isWhitePiece);
            case ROOK -> new Rook(isWhitePiece);
            case PAWN -> new Pawn(isWhitePiece);
        };
    }
}
