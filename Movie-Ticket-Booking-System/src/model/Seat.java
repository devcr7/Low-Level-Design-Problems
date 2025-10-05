package model;

import enums.SeatCategory;

public class Seat {
    private final int id;
    private final int row;
    private final SeatCategory category;

    public Seat(int id, int row, SeatCategory category) {
        this.id = id;
        this.row = row;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getRow() {
        return row;
    }

    public SeatCategory getCategory() {
        return category;
    }
}
