package com.epam.snake;

import com.epam.snake.NSnake.Direction;

public class Board {
    private final NSnake snake;
    
    public Board(NSnake snake) {
        this.snake = snake;
    }
    
    private static final int BLOCK_UNIT_SIZE = 10;
    
    private int boardWidth = 50 * BLOCK_UNIT_SIZE;
    private int boardHeight = 30 * BLOCK_UNIT_SIZE;
    
    private int xvalt;
    private int yvalt;

    public int getBoardHeight() {
        return boardHeight;
    }
    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }
    public int getXvalt() {
        return xvalt;
    }
    public void setXvalt(int xvalt) {
        this.xvalt = xvalt;
    }
    public int getYvalt() {
        return yvalt;
    }
    public void setYvalt(int yvalt) {
        this.yvalt = yvalt;
    }
    public int getBoardWidth() {
        return boardWidth;
    }
    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }
    public NSnake getSnake() {
        return snake;
    }
    public void reset() {
        // TODO Auto-generated method stub
        // reset game
        
    }
    public void moveLeft() {
        snake.setCurrentDirection(Direction.LEFT);
        xvalt = -BLOCK_UNIT_SIZE;
        yvalt = 0;
    }
    public void moveRight() {
        snake.setCurrentDirection(Direction.RIGHT);
        xvalt = +BLOCK_UNIT_SIZE;
        yvalt = 0;
    }
    public void moveUp() {
        snake.setCurrentDirection(Direction.UP);
        xvalt = 0;
        yvalt = -BLOCK_UNIT_SIZE;
    }
    public void moveDown() {
        snake.setCurrentDirection(Direction.DOWN);
        xvalt = +BLOCK_UNIT_SIZE;
        yvalt = 0;
    }
}
