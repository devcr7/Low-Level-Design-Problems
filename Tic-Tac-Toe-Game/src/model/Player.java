package model;

import enums.Symbol;
import strategy.PlayerStrategy;

public class Player {
    private final Symbol symbol;
    private final PlayerStrategy strategy;

    public Player (Symbol symbol, PlayerStrategy strategy) {
        this.symbol = symbol;
        this.strategy = strategy;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }
}
