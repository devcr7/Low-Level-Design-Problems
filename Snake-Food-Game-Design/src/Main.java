import controller.impl.SnakeGame;
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

        game.play();

        scanner.close();
        System.out.println("Thanks for playing!");
    }
}