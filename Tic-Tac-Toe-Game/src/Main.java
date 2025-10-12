import controller.impl.TicTacToeGame;
import strategy.IPlayerStrategy;
import strategy.impl.HumanPlayerStrategy;

public class Main {
    public static void main(String[] args) {
        IPlayerStrategy playerXStrategy = new HumanPlayerStrategy("Player X");
        IPlayerStrategy playerOStrategy = new HumanPlayerStrategy("Player O");

        TicTacToeGame game = new TicTacToeGame(playerXStrategy, playerOStrategy, 3);
        game.play();
    }
}