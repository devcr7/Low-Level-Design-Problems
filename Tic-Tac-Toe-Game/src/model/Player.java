package model;

import enums.Symbol;
import strategy.IPlayerStrategy;

public class Player {
    private final Symbol symbol;
    private final IPlayerStrategy strategy;

    public Player (Symbol symbol, IPlayerStrategy strategy) {
        this.symbol = symbol;
        this.strategy = strategy;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public IPlayerStrategy getStrategy() {
        return strategy;
    }
}
