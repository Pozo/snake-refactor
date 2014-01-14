package com.epam.game.snake;

import com.epam.game.entity.EntityDirection;
import com.epam.game.entity.EntityDirectionObserver;

public class SnakeDirectionObserver implements EntityDirectionObserver {
    private EntityDirection currentDirection = EntityDirection.getDefaultEntityDirection();
    
    @Override
    public boolean canMoveLeft() {
        return !currentDirection.equals(EntityDirection.RIGHT);
    }
    @Override
    public boolean canMoveRight() {
        return !currentDirection.equals(EntityDirection.LEFT);
    }
    @Override
    public boolean canMoveUp() {
        return !currentDirection.equals(EntityDirection.DOWN);
    }
    @Override
    public boolean canMoveDown() {
        return !currentDirection.equals(EntityDirection.UP);
    }
    @Override
    public EntityDirection getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentDirection(EntityDirection newDirection) {
        currentDirection = newDirection;
    }
}
