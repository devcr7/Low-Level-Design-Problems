package UtilityClasses;
import factory.PieceFactory;

import static enums.PieceType.*;

public class Board {
    private static Board instance;
    private Cell[][] board;

    private Board(int rows) {
        initializeBoard(rows);
    }

    public static Board getInstance(int rows) {
        if (instance == null) {
            instance = new Board(rows);
        }
        return instance;
    }

    private void initializeBoard(int rows) {
        board = new Cell[rows][rows];

        setPieceRow(0, true);
        setPawnRow(1, rows, true);

        setPieceRow(rows - 1, false);
        setPawnRow(rows - 2, rows, false);

        for (int row = 2; row < rows - 2; row++) {
            for (int col = 0; col < rows; col++) {
                board[row][col] = new Cell(row, col, null);
            }
        }
    }

    private void setPieceRow(int row, boolean isWhite) {
        board[row][0] = new Cell(row, 0, PieceFactory.createPiece(ROOK, isWhite));
        board[row][1] = new Cell(row, 1, PieceFactory.createPiece(KNIGHT, isWhite));
        board[row][2] = new Cell(row, 2, PieceFactory.createPiece(BISHOP, isWhite));
        board[row][3] = new Cell(row, 3, PieceFactory.createPiece(QUEEN, isWhite));
        board[row][4] = new Cell(row, 4, PieceFactory.createPiece(KING, isWhite));
        board[row][5] = new Cell(row, 5, PieceFactory.createPiece(BISHOP, isWhite));
        board[row][6] = new Cell(row, 6, PieceFactory.createPiece(KNIGHT, isWhite));
        board[row][7] = new Cell(row, 7, PieceFactory.createPiece(ROOK, isWhite));
    }

    private void setPawnRow(int row, int rows, boolean isWhite) {
        for (int col = 0; col < rows; col++) {
            board[row][col] = new Cell(row, col, PieceFactory.createPiece(PAWN, isWhite));
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }
}
