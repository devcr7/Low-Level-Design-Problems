package controller.impl;

import controller.BoardGame;
import model.Cell;
import model.Player;
import state.GameState;
import state.context.GameContext;
import state.impl.OWonState;
import state.impl.XWonState;
import strategy.PlayerStrategy;
import utilty.Board;

import static enums.Symbol.O;
import static enums.Symbol.X;

public class TicTacToeGame implements BoardGame {
    private final Board board;
    private final Player playerX;
    private final Player playerO;
    private Player currentPlayer;
    private final GameContext gameContext;

    public TicTacToeGame(PlayerStrategy xStrategy, PlayerStrategy oStrategy, int size) {
        board = Board.getInstance(size);
        playerX = new Player(X, xStrategy);
        playerO = new Player(O, oStrategy);
        currentPlayer = playerX;
        gameContext = new GameContext();
    }

    @Override
    public void play() {
        do {
            board.printBoard();

            Cell targetCell = currentPlayer.getStrategy().makeMove(board);
            board.makeMove(targetCell, currentPlayer.getSymbol());

            board.checkGameState(gameContext, currentPlayer);
            switchPlayer();
        } while (!gameContext.isGameOver());

        announceResult();
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == playerX ? playerO: playerX;
    }

    private void announceResult() {
        GameState state = gameContext.getCurrentState();
        board.printBoard();
        if (state instanceof XWonState) {
            System.out.println("Player X wins!");
        } else if (state instanceof OWonState) {
            System.out.println("Player O wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
