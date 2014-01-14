package com.epam.game.entity;

import java.awt.Point;


public interface EntityDirectionController {
    void setEntityDirection(EntityDirection newDirection);
    Point getDirection();
}
