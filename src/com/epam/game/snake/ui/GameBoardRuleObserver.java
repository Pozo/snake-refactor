package com.epam.game.snake.ui;

import java.awt.Point;

import com.epam.game.snake.Snake;

public class GameBoardRuleObserver {
    private final Point[] positions;
    private final Snake snake;

    public GameBoardRuleObserver(GameBoard gameBoard) {
        this.snake = gameBoard.getSnake();
        this.positions = gameBoard.getPositions();
    }

    public boolean hitTheWall() {
        return hitRightWall() || hitLeftWall() || hitBottomWall() || hitTopWall();
    }
    private boolean hitRightWall() {
        return (positions[0].getX() + GameBoard.BLOCK_UNIT_SIZE == GameBoard.BOARD_WIDTH);
    }
    private boolean hitLeftWall() {
        return positions[0].getX() == 0;
    }
    private boolean hitBottomWall() {
        return positions[0].getY() == 0;
    }
    private boolean hitTopWall() {
        return positions[0].getY() + GameBoard.BLOCK_UNIT_SIZE == GameBoard.BOARD_HEIGHT;
    }

    public boolean hasConflict(Point[] temp) {
        boolean hit = false;
        
        for (int i = 1; i < snake.getSize() - 1; i++) {
            if (temp[0].equals(temp[i])) {
                hit = true;
                break;
            }
        }
        return hit;
    }
}
