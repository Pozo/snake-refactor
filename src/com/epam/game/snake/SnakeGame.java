package com.epam.game.snake;

import com.epam.game.EntityDirection;
import com.epam.game.Game;

public interface SnakeGame extends Game {
    void putNewFood();
    void setSnakeDirection(EntityDirection direction);
    
    void showToplist();
    void showAbout();
    void showHelp();
}
