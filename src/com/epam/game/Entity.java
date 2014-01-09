package com.epam.game;

public interface Entity {
    public boolean canMoveLeft();
    public boolean canMoveRight();
    public boolean canMoveUp();
    public boolean canMoveDown();
    public EntityDirection getCurrentDirection();
}
