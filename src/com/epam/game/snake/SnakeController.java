package com.epam.game.snake;

import com.epam.game.EntityController;
import com.epam.game.EntityDirection;

public class SnakeController implements EntityController {

    private final SnakeGame game;

    public SnakeController(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void moveLeft() {
        game.setSnakeDirection(EntityDirection.LEFT);
    }
    @Override
    public void moveRight() {
        game.setSnakeDirection(EntityDirection.RIGHT);
    }
    @Override
    public void moveUp() {
        game.setSnakeDirection(EntityDirection.UP);
    }
    @Override
    public void moveDown() {
        game.setSnakeDirection(EntityDirection.DOWN);
    }
}
