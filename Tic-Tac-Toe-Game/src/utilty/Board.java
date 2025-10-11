package utilty;

import enums.Symbol;
import model.Cell;
import model.Player;
import state.context.GameContext;

import java.util.HashMap;
import java.util.Map;

import static enums.Symbol.*;

public class Board {
    private static Board instance;
    private int size;
    private Symbol[][] grid;
    private Map<Integer, Integer> rowSumMap;
    private Map<Integer, Integer> colSumMap;

    private int leftDiagnolSum;
    private int rightDiagnolSum;
    private boolean isGameWon;

    private Board(int size) {
        initializeBoard(size);
    }

    public static Board getInstance(int size) {
        if (instance == null) {
            instance = new Board(size);
        }

        return instance;
    }

    private void initializeBoard(int size) {
        this.size = size;

        grid = new Symbol[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++){
                grid[row][col] = EMPTY;
            }
        }

        this.rowSumMap = new HashMap<>();
        this.colSumMap = new HashMap<>();

        this.leftDiagnolSum = 0;
        this.rightDiagnolSum = 0;
        this.isGameWon = false;
    }
    public boolean isValidMove(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();

        return row >= 0 && row < size && col >= 0 && col < size && grid[row][col] == EMPTY;
    }

    public void makeMove(Cell cell, Symbol symbol) {
        int row = cell.getRow();
        int col = cell.getCol();

        int value = symbol == X ? 1 : -1;
        rowSumMap.put(row, rowSumMap.getOrDefault(row, 0) + value);
        colSumMap.put(col, colSumMap.getOrDefault(col, 0) + value);

        if (row == col) {
            leftDiagnolSum += value;
        }

        if (row + col == size - 1) {
            rightDiagnolSum += value;
        }

        if (isTargetScoreAchieved(cell, size) || isTargetScoreAchieved(cell, -size)) {
            isGameWon = true;
        }

        grid[cell.getRow()][cell.getCol()] = symbol;
    }

    private boolean isTargetScoreAchieved(Cell cell, int targetScore) {
        int row = cell.getRow();
        int col = cell.getCol();

        return rowSumMap.get(row) == targetScore || colSumMap.get(col) == targetScore
                || leftDiagnolSum == targetScore || rightDiagnolSum == targetScore;
    }

    public void checkGameState(GameContext context, Player currentPlayer) {
        if (isGameWon) {
            context.handle(currentPlayer, true);
        }
    }

    public void printBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                System.out.print(grid[row][col].getSymbol() + " ");
            }
            System.out.println();
        }
    }
}
