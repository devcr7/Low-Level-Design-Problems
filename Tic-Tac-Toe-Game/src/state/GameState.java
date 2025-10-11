package state;

import model.Player;
import state.context.GameContext;

public interface GameState {
    void handle(GameContext context, Player player, boolean hasWon);
    boolean isGameOver();
}
