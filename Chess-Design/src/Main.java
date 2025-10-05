import game.impl.ChessGame;
import UtilityClasses.Player;

public class Main {
    public static void main(String[] args) {
        Player player1 = new Player("Player 1", true);
        Player player2 = new Player("Player 2", false);

        ChessGame game = new ChessGame(player1, player2);
        game.start();
    }
}