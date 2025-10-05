package game.impl;

import enums.Direction;
import game.BoardGame;
import strategy.IMovementStrategy;
import utility.Board;
import utility.Pair;
import utility.Snake;


public class SnakeGame implements BoardGame {
    private final Board board;
    private final Snake snake;
    private final int[][] food;
    private int foodIndex;
    private final IMovementStrategy movementStrategy;

    public SnakeGame(int width, int height, int[][] food, IMovementStrategy movementStrategy) {
        this.board = Board.getInstance(width, height);
        this.food = food;
        this.foodIndex = 0;

        this.snake = new Snake();
        this.movementStrategy = movementStrategy;
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

        boolean ateFood = (foodIndex < food.length) && (food[foodIndex][0] == newHeadRow)
                && (food[foodIndex][1] == newHeadCol);

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

    public Snake getSnake() {
        return snake;
    }
}
