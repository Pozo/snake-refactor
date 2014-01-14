package com.epam.game.snake;

import java.awt.Point;

import com.epam.game.Game;
import com.epam.game.GameController;

public interface SnakeGame extends Game,SnakeGameFrame {
    void clearPlayground();
    void refreshActualScore();
    void addToToplist();
    
    Point getSnakeDirection();
    GameController getGameController();
}
