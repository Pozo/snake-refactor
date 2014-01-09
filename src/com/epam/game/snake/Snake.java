package com.epam.game.snake;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;

import com.epam.game.Entity;
import com.epam.game.EntityBrick;
import com.epam.game.EntityDirection;

public class Snake implements Entity {
    private JButton[] snakeBricks = new JButton[125];
    private Point[] _bricks = new Point[125];
    
    public static final int DEFAULT_SNAKE_SIZE = 3;
    private EntityDirection currentDirection = EntityDirection.getDefaultEntityDirection();
    
    private int size = DEFAULT_SNAKE_SIZE;
    
    private ArrayList<EntityBrick> bricks = new ArrayList<>();

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
    public void setCurrentDirection(EntityDirection currentDirection) {
        this.currentDirection = currentDirection;
    }
    public int getSize() {
        return size;
    }
    public void incrementSize() {
        this.size++;
    }
    public ArrayList<EntityBrick> getBricks() {
        return bricks;
    }
    public void addBrick(EntityBrick position) {
        this.bricks.add(position);
    }
    public void swapBricks() {
        for (int i = 1; i < size; i++) {
            snakeBricks[i].setLocation(_bricks[i - 1]);
        }
    }
}
