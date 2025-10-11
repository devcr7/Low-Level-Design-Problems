import controller.impl.TicTacToeGame;
import strategy.PlayerStrategy;
import strategy.impl.HumanPlayerStrategy;

public class Main {
    public static void main(String[] args) {
        PlayerStrategy playerXStrategy = new HumanPlayerStrategy("Player X");
        PlayerStrategy playerOStrategy = new HumanPlayerStrategy("Player O");

        TicTacToeGame game = new TicTacToeGame(playerXStrategy, playerOStrategy, 3);
        game.play();
    }
}