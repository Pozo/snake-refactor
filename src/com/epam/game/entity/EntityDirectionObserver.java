package com.epam.game.entity;

public interface EntityDirectionObserver {
    public boolean canMoveLeft();
    public boolean canMoveRight();
    public boolean canMoveUp();
    public boolean canMoveDown();
    public EntityDirection getCurrentDirection();
}
