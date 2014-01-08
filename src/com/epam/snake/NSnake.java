package com.epam.snake;

public class NSnake {
    public enum Direction {
        UP(0x1),
        DOWN(0x2),
        LEFT(0x4),
        RIGHT(0x8);
        
        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Direction currentDirection = Direction.RIGHT;
    private int size = 3;
   
    public boolean canMoveLeft() {
        return !currentDirection.equals(Direction.RIGHT);
    }
    public boolean canMoveRight() {
        return !currentDirection.equals(Direction.LEFT);
    }
    public boolean canMoveUp() {
        return !currentDirection.equals(Direction.DOWN);
    }
    public boolean canMoveDown() {
        return !currentDirection.equals(Direction.UP);
    }
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
    public int getSize() {
        return size;
    }
    public void incrementSize() {
        this.size++;
    }
}
