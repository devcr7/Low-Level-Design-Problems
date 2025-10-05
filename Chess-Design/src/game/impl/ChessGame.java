package game.impl;

import UtilityClasses.Board;
import UtilityClasses.Cell;
import UtilityClasses.Move;
import UtilityClasses.Player;
import enums.Status;
import game.BoardGame;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static enums.Status.*;

public class ChessGame implements BoardGame {
    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;

    boolean isWhiteTurn;
    private final List<Move> gameLog;
    private Status status;

    public ChessGame(Player whitePlayer, Player blackPlayer) {
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.board = Board.getInstance(8);
        this.isWhiteTurn = true;
        this.gameLog = new ArrayList<>();
        this.status = ACTIVE;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (this.status == ACTIVE) {
            Player currentPlayer = isWhiteTurn ? whitePlayer : blackPlayer;
            System.out.println(currentPlayer.getName() + "'s turn (" + (currentPlayer.isWhiteSide() ? "White": "Black") + ")");

            System.out.println("Enter source coordinates");
            int startRow = scanner.nextInt();
            int startCol = scanner.nextInt();

            System.out.println("Enter destination coordinates");
            int endRow = scanner.nextInt();
            int endCol = scanner.nextInt();

            Cell startCell = board.getCell(startRow, startCol);
            Cell endCell = board.getCell(endRow, endCol);

            if (startCell == null || startCell.getPiece() == null) {
                System.out.println("Invalid move: No piece at source cell.");
                continue;
            }

            makeMove(new Move(startCell, endCell), currentPlayer);
            getSnapShot();
        }

        System.out.println("Game over! Status: " + this.status);
    }

    private void getSnapShot() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece currentPiece = board.getCell(row, col).getPiece();
                if (currentPiece == null) {
                    System.out.print(".  ");
                    continue;
                }
                System.out.print(currentPiece.getPieceType().toString() + " (" + (currentPiece.isWhite() ? "White" : "Black") + ")  ");
            }
            System.out.println();
        }
    }

    private void makeMove(Move move, Player player) {
        if (move.isValid()) {
            Piece sourcePiece = move.getStartCell().getPiece();

            if (sourcePiece.canMove(this.board, move.getStartCell(), move.getEndCell())) {
                Piece destinationPiece = move.getEndCell().getPiece();

                if(destinationPiece != null) {
                    if (destinationPiece instanceof King) {
                        if (isWhiteTurn) this.status = WHITE_WIM;
                        else this.status = BLACK_WIN;
                        return;
                    }

                    destinationPiece.setKilled(true);
                }

                gameLog.add(move);
                move.getStartCell().setPiece(null);
                move.getEndCell().setPiece(sourcePiece);

                this.isWhiteTurn = !isWhiteTurn;
            }
        }
    }
}
