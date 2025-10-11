package state.impl;

import model.Player;
import state.GameState;
import state.context.GameContext;

public class OWonState implements GameState {
    @Override
    public void handle(GameContext context, Player player, boolean hasWon) {
        // nothing
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}
