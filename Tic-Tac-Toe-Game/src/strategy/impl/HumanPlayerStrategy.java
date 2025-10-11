package strategy.impl;

import model.Cell;
import strategy.PlayerStrategy;
import utilty.Board;

import java.util.Scanner;

public class HumanPlayerStrategy implements PlayerStrategy {
    private final Scanner scanner;
    private final String playerName;

    public HumanPlayerStrategy (String playerName) {
        scanner = new Scanner(System.in);
        this.playerName = playerName;
    }

    @Override
    public Cell makeMove(Board board) {
        while (true) {
            System.out.println(playerName + " please make your move");

            try {
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                Cell cell = new Cell(row, col);

                if (board.isValidMove(cell)) {
                    return cell;
                }

                System.out.println("Invalid move, please try again");
            } catch (Exception exception) {
                System.out.println("Invalid input. Please enter numeric values");
                scanner.nextLine();
            }
        }
    }
}
