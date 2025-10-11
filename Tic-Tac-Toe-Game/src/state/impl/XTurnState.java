package state.impl;

import model.Player;
import state.GameState;
import state.context.GameContext;

import static enums.Symbol.X;

public class XTurnState implements GameState {
    @Override
    public void handle(GameContext context, Player player, boolean hasWon) {
        if (hasWon) {
            context.setState(player.getSymbol() == X ? new XWonState() : new OWonState());
        }else {
            context.setState(new OTurnState());
        }
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
