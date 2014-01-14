package com.epam.game.snake.entity;

import com.epam.game.entity.EntityController;
import com.epam.game.entity.EntityDirection;
import com.epam.game.snake.SnakeGame;

public class SnakeController implements EntityController {

    private final SnakeGame game;

    public SnakeController(SnakeGame game) {
        this.game = game;
    }

    @Override
    public void moveLeft() {
        game.getGameController().getGameEntityDirectionController().setEntityDirection(EntityDirection.LEFT);
    }
    @Override
    public void moveRight() {
        game.getGameController().getGameEntityDirectionController().setEntityDirection(EntityDirection.RIGHT);
    }
    @Override
    public void moveUp() {
        game.getGameController().getGameEntityDirectionController().setEntityDirection(EntityDirection.UP);
    }
    @Override
    public void moveDown() {
        game.getGameController().getGameEntityDirectionController().setEntityDirection(EntityDirection.DOWN);
    }
}
