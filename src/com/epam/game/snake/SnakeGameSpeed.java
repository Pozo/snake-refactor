package com.epam.game.snake;

public enum SnakeGameSpeed {
    SLOW(50),NORMAL(70),FAST(90);
    
    private final int speed;

    SnakeGameSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
