package com.epam.game;

import java.awt.Point;

public class EntityBrick {
    private final Point point;

    public EntityBrick(Point point) {
        this.point = point;
    }
    
    public Point getPoint() {
        return point;
    }
}
