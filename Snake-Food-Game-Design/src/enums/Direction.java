package enums;

public enum Direction {
    UP("W"),
    DOWN("S"),
    LEFT("A"),
    RIGHT("D");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public static Direction fromString(String dir) {
        for (Direction direction: Direction.values()) {
            if (direction.getDirection().equals(dir)) {
                return direction;
            }
        }
        return null;
    }

    public String getDirection() {
        return direction;
    }
}
