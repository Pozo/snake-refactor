package com.epam.game.snake.entity;

import java.awt.Point;

import com.epam.game.Game;
import com.epam.game.entity.EntityDirection;
import com.epam.game.entity.EntityDirectionController;
import com.epam.game.snake.ui.GameBoard;

public class SnakeEntityDirectionController implements EntityDirectionController {
    private Point direction = new Point(+GameBoard.BLOCK_UNIT_SIZE, 0);
    private final Game game;
    
    public SnakeEntityDirectionController(Game game) {
        this.game = game;
    }
    
    @Override
    public void setEntityDirection(EntityDirection newDirection) {
        switch (newDirection) {
        case UP:
            direction.setLocation(0, -GameBoard.BLOCK_UNIT_SIZE);
            break;
        case DOWN:
            direction.setLocation(0, +GameBoard.BLOCK_UNIT_SIZE);
            break;
        case LEFT:
            direction.setLocation(-GameBoard.BLOCK_UNIT_SIZE, 0);
            break;
        case RIGHT:
        default:
            direction.setLocation(+GameBoard.BLOCK_UNIT_SIZE, 0);
            break;
        }
        game.getPlayground().getSnake().setCurrentDirection(newDirection);
    }

    @Override
    public Point getDirection() {
        return direction;
    }

}
