package state.context;

import model.Player;
import state.GameState;
import state.impl.XTurnState;

public class GameContext {
    private GameState currentState;

    public GameContext() {
        this.currentState = new XTurnState();
    }

    public void handle(Player player, boolean hasWon) {
        currentState.handle(this, player, hasWon);
    }

    public boolean isGameOver() {
        return currentState.isGameOver();
    }

    public void setState(GameState state) {
        currentState = state;
    }

    public GameState getCurrentState() {
        return currentState;
    }
}
