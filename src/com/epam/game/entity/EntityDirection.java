package com.epam.game.entity;

public enum EntityDirection {
    UP(0x1),
    DOWN(0x2),
    LEFT(0x4),
    RIGHT(0x8);
    
    private final int value;

    EntityDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static EntityDirection getDefaultEntityDirection() {
        return RIGHT;
    }
}
