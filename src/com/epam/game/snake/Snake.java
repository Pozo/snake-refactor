package com.epam.game.snake;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JButton;

import com.epam.game.entity.EntityBrick;
import com.epam.game.entity.EntityDirection;
import com.epam.game.entity.EntityDirectionObserver;

public class Snake {
    private JButton[] snakeBricks = new JButton[125];
    private Point[] _bricks = new Point[125];
    
    public static final int DEFAULT_SNAKE_SIZE = 3;
    private final SnakeDirectionObserver controller = new SnakeDirectionObserver();
    
    private int size = DEFAULT_SNAKE_SIZE;
    
    private ArrayList<EntityBrick> bricks = new ArrayList<>();

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
    public EntityDirectionObserver getController() {
        return controller;
    }
    public void setCurrentDirection(EntityDirection newDirection) {
        controller.setCurrentDirection(newDirection);
        
    }
}
