package com.epam.game;

public enum GameSpeed {
    SLOW(50),NORMAL(70),FAST(90);
    
    private final int speed;

    GameSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
