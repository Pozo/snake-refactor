package com.epam.game;

import java.awt.Point;

public interface EntityController {
    void moveLeft();
    void moveRight();
    void moveUp();
    void moveDown();
    boolean hasConflict(Point[] temp);
}
