package controller.impl;

import enums.Direction;
import controller.BoardGame;
import strategy.IMovementStrategy;
import utility.Board;
import utility.Pair;
import utility.Snake;

import java.util.Scanner;


public class SnakeGame implements BoardGame {
    private final Board board;
    private final Snake snake;
    private final int[][] food;
    private int foodIndex;
    private final IMovementStrategy movementStrategy;
    private final Scanner scanner;

    public SnakeGame(int width, int height, int[][] food, IMovementStrategy movementStrategy) {
        this.board = Board.getInstance(width, height);
        this.food = food;
        this.foodIndex = 0;

        this.snake = new Snake();
        this.movementStrategy = movementStrategy;
        scanner = new Scanner(System.in);
    }

    @Override
    public void play() {
        int score = 0;
        boolean gameRunning = true;

        while (gameRunning) {
            displayGameState();

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

            score = move(direction);
            if (score == -1) {
                System.out.println("GAME OVER!");
                System.out.println("Final score: " + (snake.getBody().size() - 1));
                gameRunning = false;
            } else {
                System.out.println("Score: " + score);
            }
        }
    }

    public int move(Direction direction) {
        Pair currentHead = snake.getBody().peekFirst();

        Pair newHead = movementStrategy.getNextPosition(currentHead, direction);
        int newHeadRow = newHead.getRow();
        int newHeadCol = newHead.getCol();

        boolean crossedBoundary = newHeadRow < 0 || newHeadRow >= board.getHeight()
                || newHeadCol < 0 || newHeadCol >= board.getWidth();

        Pair currentTail = snake.getBody().peekLast();

        // exclude tail as it will move away
        boolean bitesItself = snake.getPositionSet().contains(newHead)
                && !(newHead.getRow() == currentTail.getRow() && newHead.getCol() == currentTail.getCol());

        if (crossedBoundary || bitesItself) {
            return -1;
        }

        boolean ateFood = (foodIndex < food.length) && (food[foodIndex][0] == newHeadRow) && (food[foodIndex][1] == newHeadCol);

        if (ateFood) {
            foodIndex++;
        } else {
            snake.getBody().pollLast();
            snake.getPositionSet().remove(currentTail);
        }

        snake.getBody().addFirst(newHead);
        snake.getPositionSet().add(newHead);

        return snake.getBody().size() - 1;
    }

    private void displayGameState() {
        System.out.println("nCurrent snake length: " + snake.getBody().size());
    }
}
