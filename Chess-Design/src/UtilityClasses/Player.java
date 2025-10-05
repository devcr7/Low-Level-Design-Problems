package UtilityClasses;

public class Player {
    private final String name;
    private final boolean isWhiteSide;

    public Player(String name, boolean isWhite) {
        this.name = name;
        this.isWhiteSide = isWhite;
    }

    public String getName() {
        return name;
    }

    public boolean isWhiteSide() {
        return isWhiteSide;
    }
}
