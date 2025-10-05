import enums.Direction;
import game.impl.SnakeGame;
import strategy.impl.HumanMovementStrategy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter width and height of the board: ");
        int width = scanner.nextInt();
        int height = scanner.nextInt();
        scanner.nextLine();

        int[][] foodPositions = {
                {0, 1},
                {10, 8},
                {3, 12},
                {8, 17},
                {12, 3}
        };

        SnakeGame game = new SnakeGame(width, height, foodPositions, new HumanMovementStrategy());

        System.out.println("========SNAKE GAME========");
        System.out.println("Controls: W (Up), S (Down), A (Left), D (Right), Q (Quit)");
        System.out.println("==========================");
        System.out.println();

        boolean gameRunning = true;
        int score = 0;

        while (gameRunning) {
            displayGameState(game);

            System.out.print("Enter move (W/A/S/D) or Q to quit: ");
            String input = scanner.nextLine().toUpperCase();

            if(input.equals("Q")) {
                System.out.println("Game ended by player. Final Score " + score);
                gameRunning = false;
                continue;
            }

            Direction direction = Direction.fromString(input);
            if (direction == null) {
                System.out.println("Invalid input");
                continue;
            }

            score = game.move(direction);
            if (score == -1) {
                System.out.println("GAME OVER!");
                System.out.println("Final score: " + (game.getSnake().getBody().size() - 1));
                gameRunning = false;
            } else {
                System.out.println("Score: " + score);
            }
        }

        scanner.close();
        System.out.println("Thanks for playing!");
    }

    private static void displayGameState(SnakeGame game) {
        System.out.println("nCurrent snake length: " + game.getSnake().getBody().size());
    }
}