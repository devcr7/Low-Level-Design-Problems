package model;

public abstract class FoodItem {
    protected final int row;
    protected final int column;

    protected int points;

    protected FoodItem(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPoints() {
        return points;
    }
}
